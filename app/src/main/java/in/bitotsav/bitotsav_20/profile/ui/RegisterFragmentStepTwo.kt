package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragmentStepTwo : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    companion object {
        fun newInstance() = RegisterFragmentStepTwo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val email = arguments?.get("email")
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
            R.id.verify_btn -> navController.navigate(R.id.action_registerFragmentStepTwo_to_profileFragment)
        }
    }
}
