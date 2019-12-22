package `in`.bitotsav.bitotsav_20.leaderboard.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.leaderboard.data.Team
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leaderboard.*

/**
 * A simple [Fragment] subclass.
 */
class LeaderboardFragment : Fragment() {

    private val teams = listOf(
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847),
        Team("12ks13dak", "teamName", "teamMembers", 222, 7847)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leaderboard_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = LeaderboardAdapter(teams)
        }
    }
}
