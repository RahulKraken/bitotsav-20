package `in`.bitotsav.bitotsav_20.schedule.ui


import `in`.bitotsav.bitotsav_20.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        schedule_view_pager.adapter = DayPagerAdapter(activity!!.supportFragmentManager)
    }

    inner class DayPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> ScheduleDayFragment("1")
                1 -> ScheduleDayFragment("2")
                2 -> ScheduleDayFragment("3")
                else -> ScheduleDayFragment("1")
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "DAY 1"
                1 -> "DAY 2"
                2 -> "DAY 3"
                else -> "DAY 1"
            }
        }
    }
}
