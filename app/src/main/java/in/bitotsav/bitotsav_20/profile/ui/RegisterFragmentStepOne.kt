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
class RegisterFragmentStepOne : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

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
            R.id.register_btn -> navController.navigate(R.id.action_registerFragmentStepOne_to_registerFragmentStepTwo)
            R.id.login_label -> navController.navigate(R.id.action_registerFragmentStepOne_to_loginFragment)
        }
    }
}
