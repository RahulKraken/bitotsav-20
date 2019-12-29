package `in`.bitotsav.bitotsav_20.profile.ui


import `in`.bitotsav.bitotsav_20.Bitotsav
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_register_fragment_step_one.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragmentStepOne : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

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
        val email = register_email.text.toString()
        val pass = register_password.text.toString()
        val confirmPass = register_password_confirm.text.toString()

        // TODO: add pass == confirmPass check
        if (email.isNotBlank() && pass.isNotBlank() && confirmPass.isNotBlank() && pass == confirmPass) {
            register(email, pass)
        }
    }

    private fun navigateToVerify() {
        navController.navigate(R.id.action_registerFragmentStepOne_to_registerFragmentStepTwo,
            bundleOf(
                "email" to register_email.text.toString()
            ))
    }
}
