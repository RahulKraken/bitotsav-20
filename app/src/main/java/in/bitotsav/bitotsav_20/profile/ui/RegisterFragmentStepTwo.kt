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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_register_fragment_step_two.*
import org.json.JSONObject
import java.lang.NumberFormatException

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragmentStepTwo : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    lateinit var email: String
    lateinit var token: String
    lateinit var phone: String

    companion object {
        fun newInstance() = RegisterFragmentStepTwo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        email = arguments?.get("email") as String
        phone = arguments?.get("phone") as String
        token = arguments?.get("token") as String
        println("argument received step two email: $email, phone: $phone and token: $token")
        return inflater.inflate(R.layout.fragment_register_fragment_step_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<MaterialButton>(R.id.verify_btn).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.verify_btn -> tryToVerify()
        }
    }

    private fun tryToVerify() {
        val name = register_name.text.toString()
        val gender = register_gender.text.toString().toIntOrNull()
        val clgName = register_clg_name.text.toString()
        val clgCity = register_clg_city.text.toString()
        val clgState = register_clg_state.text.toString()
        val clgId = register_clg_id.text.toString()
        val emailOtp = register_email_otp.text.toString()
        val mobileOtp = register_mobile_otp.text.toString()

        // TODO: add else block
        if (name.isNotBlank() && (gender == 1 || gender == 2) && clgName.isNotBlank() && clgCity.isNotBlank() && clgState.isNotBlank() && clgId.isNotBlank() && emailOtp.isNotBlank() && mobileOtp.isNotBlank()) {
            sendVerificationRequest(name, gender, clgName, clgCity, clgState, clgId, emailOtp, mobileOtp)
        }
    }

    private fun sendVerificationRequest(
        name: String,
        gender: Int,
        clgName: String,
        clgCity: String,
        clgState: String,
        clgId: String,
        emailOtp: String,
        mobileOtp: String
    ) {
        val request = object : StringRequest(Method.POST, "https://bitotsav.in/api/auth/verify",
            Response.Listener {response ->
                println("response: $response")
                // TODO: enclose in try-catch : JSONException
                val res = JSONObject(response)
                val status = res.get("status")
                println("status: $status")
                when (status) {
                    200 -> {
                        val user = User(-1, name, email, phone, gender, clgName, clgCity, clgState, clgId, res.getBoolean("isVerified"))
                        SharedPrefUtils(context!!).setToken(res.getString("token"))
                        checkVerificationStatusAndSave(user)
                        println("token: ${res.get("token")}, isVerified: ${res.get("isVerified")}")
                    }
                    else -> println("message: ${res.get("message")}")
                }
        }, Response.ErrorListener {
                println("Unknown error occurred!!")
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "x-access-token" to token
                )
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val body = JSONObject()
                body.put("emailOTP", emailOtp)
                body.put("mobileOTP", mobileOtp)
                body.put("gender", gender)
                body.put("name", name)
                body.put("clgName", clgName)
                body.put("clgCity", clgCity)
                body.put("clgState", clgState)
                body.put("clgId", clgId)
                return body.toString().toByteArray(Charsets.UTF_8)
            }
        }

        VolleyService.getRequestQueue(context!!).add(request)
    }

    private fun checkVerificationStatusAndSave(user: User) {
        // TODO: Replace with /getUserDashboard endpoint
        val request = object : StringRequest(Method.GET, "https://bitotsav.in/api/auth/getUserState",
            Response.Listener { response ->
                println(response)
                // TODO: Inside try-catch
                val res = JSONObject(response)
                if (res.get("verified") != null) {
                    saveAndNavigate(user)
                }
            },
            Response.ErrorListener {
                println("step two: error occure - ${it.message}")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "x-access-token" to token
                )
            }
        }
        VolleyService.getRequestQueue(context!!).add(request)
    }

    // TODO: replace with just navigation after /getUserDashboard endpoint is active
    private fun saveAndNavigate(user: User) {
        SharedPrefUtils(context!!).setUser(user)
        navController.navigate(R.id.action_registerFragmentStepTwo_to_profileFragment)
    }

    // TODO: redundant - remove it
    private fun navigateToProfile() {
        navController.navigate(R.id.action_registerFragmentStepTwo_to_profileFragment)
    }
}
