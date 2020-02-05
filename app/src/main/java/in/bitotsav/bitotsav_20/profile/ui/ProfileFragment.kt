package `in`.bitotsav.bitotsav_20.profile.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    lateinit var navController: NavController

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (SharedPrefUtils(context!!).getUser() == null)
            navController.navigate(R.id.action_profileFragment_to_loginFragment)
        else bindUser(SharedPrefUtils(context!!).getUser())

        profile_logout_btn.setOnClickListener {
            logout()
        }

        profile_verify_btn.setOnClickListener {
            verifyAccount()
        }
    }

    private fun verifyAccount() {
        if (SharedPrefUtils(context!!).getUser() != null) {
            val user = SharedPrefUtils(context!!).getUser()
            navController.navigate(R.id.action_profileFragment_to_registerFragmentStepTwo)
        }
    }

    private fun logout() {
        SharedPrefUtils(context!!).setUser(null)
        activity?.finish()
    }

    private fun bindUser(user: User?) {
        if (user?.isVerified == true) profile_verify_btn.visibility = View.GONE
        profile_email.text = user?.email
        profile_phone.text = user?.phoneNo
        profile_bitotsav_id.text = user?.bitotsavId.toString()
        profile_name.text = user?.name
        profile_clg_id.text = user?.clgId
        profile_clg_name.text = user?.clgName
        profile_clg_city.text = user?.clgCity
    }
}
