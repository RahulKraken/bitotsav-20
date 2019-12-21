package `in`.bitotsav.bitotsav_20.event.ui


import `in`.bitotsav.bitotsav_20.BitotsavFragment
import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.event.ui.days.DayOneFragment
import `in`.bitotsav.bitotsav_20.event.ui.days.DayThreeFragment
import `in`.bitotsav.bitotsav_20.event.ui.days.DayTwoFragment
import `in`.bitotsav.bitotsav_20.feed.ui.FeedFragment
import `in`.bitotsav.bitotsav_20.leaderboard.ui.LeaderboardFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    class DayPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> DayOneFragment()
                1 -> DayTwoFragment()
                2 -> DayThreeFragment()
                else -> DayOneFragment()
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
