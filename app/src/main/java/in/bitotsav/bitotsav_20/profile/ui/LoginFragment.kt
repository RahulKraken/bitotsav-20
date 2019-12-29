package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.config.Secret
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.login_email
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var email: String
    private lateinit var password: String

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<MaterialButton>(R.id.register_label).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.login_btn).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_btn -> startLogin()
            R.id.register_label -> navController.navigate(R.id.action_loginFragment_to_registerFragmentStepOne)
        }
    }

    private fun startLogin() {
        email = login_email.text.toString()
        password = login_password.text.toString()
        if (email.isNotBlank() && password.isNotBlank()) {
            verifyCaptcha()
        }
    }

    private fun verifyCaptcha() {
        SafetyNet.getClient(activity!!).verifyWithRecaptcha(Secret.recptchaSiteKey)
            .addOnSuccessListener(activity!!) { response ->
                println("recaptcha success: ${response.tokenResult}")
                if (response.tokenResult.isNotEmpty()) {
                    attemptLogin(response.tokenResult)
                }
            }
            .addOnFailureListener(activity!!) {
                println("recaptcha failed: $it")
            }
    }

    private fun attemptLogin(token: String) {
        val loginRequest = object : StringRequest(Method.POST, "https://bitotsav.in/api/auth/login",
            Response.Listener {response ->
                val res = JSONObject(response)
                println("login: ${res.get("status")}")
                when (res.get("status")) {
                    200 -> println(res)
                    else -> println(res)
                }
            }, Response.ErrorListener {
                println("Unknown error occurred")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "captcha" to token
                )
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val body = JSONObject()
                body.put("email", email)
                body.put("password", password)
                return body.toString().toByteArray(Charsets.UTF_8)
            }
        }

        VolleyService.getRequestQueue(context!!).add(loginRequest)
    }

    private fun navigateToProfile() {
        navController.navigate(R.id.action_loginFragment_to_profileFragment)
    }
}
