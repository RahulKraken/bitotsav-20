package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.config.Secret
import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register_fragment_step_one.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragmentStepOne : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    var email = ""
    var phone = ""

    var user = User(-1, null, email, phone, null, null, null, null, null, null, false, null, null, null)

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
            validateCaptcha(email, pass, phone)
        }
    }

    private fun validateCaptcha(
        email: String,
        password: String,
        phone: String
    ) {
        reg_one_progress_bar.visibility = View.VISIBLE
        SafetyNet.getClient(activity!!).verifyWithRecaptcha(Secret.recptchaSiteKey)
            .addOnSuccessListener(activity!!) {response ->
                println("recaptcha success: ${response.tokenResult}")
                reg_one_progress_bar.visibility = View.GONE
                if (response.tokenResult.isNotEmpty()) register(email, password, phone, response.tokenResult)
            }
            .addOnFailureListener(activity!!) {
                println("recaptcha failed: $it")
                reg_one_progress_bar.visibility = View.GONE
                Snackbar.make(parent_reg_one_frag, "recaptcha failed", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun register(
        email: String,
        password: String,
        phone: String,
        token: String
    ) {
        reg_one_progress_bar.visibility = View.VISIBLE
        val request = object : StringRequest(Method.POST, "https://bitotsav.in/api/auth/register", Response.Listener { response ->
            reg_one_progress_bar.visibility = View.GONE
            println("success: $response")
            val obj = JSONObject(response)
            println(obj.get("status"))
            when (obj.get("status")) {
                200 -> saveAndNavigate(obj)
                else -> {
                    println(obj.get("message"))
                    Snackbar.make(parent_reg_one_frag, obj.getString("message"), Snackbar.LENGTH_SHORT).show()
                }
            }
        }, Response.ErrorListener {error ->
            reg_one_progress_bar.visibility = View.GONE
            println("error: $error")
            Snackbar.make(parent_reg_one_frag, "Unknown error occurred!", Snackbar.LENGTH_SHORT).show()
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
                body.put("captchaToken", token)
                return body.toString().toByteArray(Charsets.UTF_8)
            }
        }

        VolleyService.getRequestQueue(context!!).add(request)
    }

    private fun saveAndNavigate(res: JSONObject) {
        user = User(-1, null, email, phone, null, null, null, null, null, res.getBoolean("isVerified"), false, null, null, null)
        println("saving user: $user")
        SharedPrefUtils(context!!).setToken(res.getString("token"))
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
