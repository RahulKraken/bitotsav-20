package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_register_fragment_step_two.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragmentStepTwo : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    lateinit var email: String
    lateinit var token: String

    companion object {
        fun newInstance() = RegisterFragmentStepTwo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        email = arguments?.get("email") as String
        println("argument received step two $email")
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
        val gender = register_gender.text.toString()
        val phone = register_phone.text.toString()
        val clgName = register_clg_name.text.toString()
        val clgCity = register_clg_city.text.toString()
        val clgState = register_clg_state.text.toString()
        val clgId = register_clg_id.text.toString()
        val otp = register_otp.text.toString()

        // TODO: Check for not empty fields

        SharedPrefUtils(context!!).setUser(User(93, name, email, phone, 54, clgName, clgCity, clgState, clgId, true, "lkds", 45))
        navigateToProfile()
    }

    private fun navigateToProfile() {
        navController.navigate(R.id.action_registerFragmentStepTwo_to_profileFragment)
    }
}
