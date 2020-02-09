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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
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
                Snackbar.make(parent_login_frag, it.toString(), Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun attemptLogin(token: String) {
        login_progress_bar.visibility = View.VISIBLE
        val loginRequest = object : StringRequest(Method.POST, "https://bitotsav.in/api/auth/login",
            Response.Listener {response ->
                login_progress_bar.visibility = View.GONE
                val res = JSONObject(response)
                println("login: ${res.get("status")}")
                when (res.get("status")) {
                    200 -> {
                        println(res)
                        getUserData(res.getString("token"), res.getBoolean("isVerified"))
                    }
                    else -> {
                        println(res)
                        Snackbar.make(login_btn, res.getString("message"), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }, Response.ErrorListener {
                login_progress_bar.visibility = View.GONE
                println("Unknown error occurred")
                Snackbar.make(login_btn, "Unknown error occurred", Snackbar.LENGTH_SHORT).show()
            }) {
            // captcha token to be passed in request body
            /*override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "captchaToken" to token
                )
            }*/

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val body = JSONObject()
                body.put("email", email)
                body.put("password", password)
                body.put("client", "app")
                body.put("captchaToken", token)
                return body.toString().toByteArray(Charsets.UTF_8)
            }
        }

        VolleyService.getRequestQueue(context!!).add(loginRequest)
    }

    private fun getUserData(token: String, isVerified: Boolean) {
        login_progress_bar.visibility = View.VISIBLE
        val dataReq = object : StringRequest(
            Method.GET, "https://bitotsav.in/api/dash/getProfile",
            Response.Listener {response ->
                login_progress_bar.visibility = View.GONE
                val res = JSONObject(response)
                saveAndNavigate(token, isVerified, res.getJSONObject("user"))
            }, Response.ErrorListener {error ->
                login_progress_bar.visibility = View.GONE
                println("getUserData: $error")
                Snackbar.make(login_btn, "Unknown error occurred", Snackbar.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "x-access-token" to token
                )
            }
        }

        VolleyService.getRequestQueue(context!!).add(dataReq)
    }

    private fun saveAndNavigate(token: String, isVerified: Boolean, user: JSONObject) {
        SharedPrefUtils(context!!).setToken(token)
        val u = User(user.getInt("bitotsavId"), user.getString("name"), user.getString("email"), user.getString("phoneNo"), user.getInt("gender"), user.getString("clgName"), user.getString("clgCity"), user.getString("clgState"), user.getString("clgId"), isVerified)
        println("user found: $u")
        SharedPrefUtils(context!!).setUser(u)
        navController.navigate(R.id.action_loginFragment_to_profileFragment)
    }
}
