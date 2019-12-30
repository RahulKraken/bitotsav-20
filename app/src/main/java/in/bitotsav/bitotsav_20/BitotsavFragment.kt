package `in`.bitotsav.bitotsav_20


import `in`.bitotsav.bitotsav_20.profile.ui.ProfileActivity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
class BitotsavFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = BitotsavFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bitotsav, container, false)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.user_detail_container -> startActivity(Intent(activity!!, ProfileActivity::class.java))
        }
    }
}
