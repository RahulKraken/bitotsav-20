package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.config.Secret
import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.login_email
import kotlinx.android.synthetic.main.fragment_register_fragment_step_one.*
import java.util.concurrent.Executor

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
            verifyCaptcha(email, password)
        }
    }

    private fun verifyCaptcha(email: String, password: String) {
        SafetyNet.getClient(activity!!).verifyWithRecaptcha(Secret.recptchaSiteKey)
            .addOnSuccessListener(this as Executor, OnSuccessListener {response ->
                println("recaptcha success: $response")
                SharedPrefUtils(context!!).setUser(User(873, "name", "email", "7845221", 2, "alkd", "kdf;l", "kado", "kdlao", false))
                navigateToProfile()
            })
            .addOnFailureListener(this as Executor, OnFailureListener {
                println("recaptcha failed: $it")
            })
    }

    private fun navigateToProfile() {
        navController.navigate(R.id.action_loginFragment_to_profileFragment)
    }
}
