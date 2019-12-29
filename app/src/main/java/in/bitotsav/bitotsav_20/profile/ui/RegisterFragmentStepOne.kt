package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_register_fragment_step_one.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragmentStepOne : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    var email = ""
    var phone = ""

    var user = User(-1, null, email, phone, null, null, null, null, null, null)

    companion object {
        fun newInstance() = RegisterFragmentStepOne()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_fragment_step_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<MaterialButton>(R.id.register_btn).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.login_label).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.register_btn -> tryToRegister()
            R.id.login_label -> navController.navigate(R.id.action_registerFragmentStepOne_to_loginFragment)
        }
    }

    private fun tryToRegister() {
        email = register_email.text.toString()
        phone = register_phone.text.toString()
        val pass = register_password.text.toString()
        val confirmPass = register_password_confirm.text.toString()

        if (email.isNotBlank() && phone.isNotBlank() && pass.isNotBlank() && confirmPass.isNotBlank() && pass == confirmPass) {
            register(email, pass, phone)
        }
    }

    private fun register(email: String, password: String, phone: String) {
        val request = object : StringRequest(Method.POST, "https://bitotsav.in/api/auth/register", Response.Listener { response ->
            println("success: $response")
            val obj = JSONObject(response)
            println(obj.get("status"))
            when (obj.get("status")) {
                // TODO: replace with snackBar
                500, 422, 401, 415, 400 -> println(obj.get("message"))
                200 -> saveAndNavigate(obj)
                else -> println(obj.get("message"))
            }
        }, Response.ErrorListener {error ->
            println("error: $error")
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val body = JSONObject()
                body.put("email", email)
                body.put("password", password)
                body.put("confPassword", password)
                body.put("phoneNo", phone)
                body.put("client", "app")
                return body.toString().toByteArray(Charsets.UTF_8)
            }
        }

        VolleyService.getRequestQueue(context!!).add(request)
    }

    private fun saveAndNavigate(res: JSONObject) {
        user = User(-1, null, email, phone, null, null, null, null, null, res.getBoolean("isVerified"))
        println("saving user: $user")
        SharedPrefUtils(context!!).setUser(user)
        navController.navigate(R.id.action_registerFragmentStepOne_to_registerFragmentStepTwo,
            bundleOf("email" to email,
                "phone" to phone,
                "token" to res.getString("token")))
    }

    private fun navigateToVerify() {
        navController.navigate(R.id.action_registerFragmentStepOne_to_registerFragmentStepTwo,
            bundleOf(
                "email" to register_email.text.toString()
            ))
    }
}
