package `in`.bitotsav.bitotsav_20.leaderboard.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.feed.ui.ItemDividerDecoration
import `in`.bitotsav.bitotsav_20.leaderboard.data.Team
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class LeaderboardFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderboardFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // display user scorecard
//        if(SharedPrefUtils(context!!).getUser() == null) {
//            user_score_card.visibility = View.GONE
//        }

        val LEADERBOARD_URL = "https://bitotsav.in/api/admin/leaderboard"
        val leaderboardRequest = StringRequest(Request.Method.POST, LEADERBOARD_URL,
            Response.Listener {response ->
                val res = JSONObject(response)
                val leaderboard = res.getJSONArray("leaderboard")
                val teams = ArrayList<Team>()
                for (i in 0 until leaderboard.length()) {
                    val curr = leaderboard.getJSONObject(i)
                    teams.add(Team(curr.getInt("teamId"), curr.getString("teamName"), null, i + 1, curr.getInt("points")))
                }
                leaderboard_rv.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = LeaderboardAdapter(teams)
                    addItemDecoration(ItemDividerDecoration(context, 32, 32))
                }
        }, Response.ErrorListener {
                println("leaderboard: error")
        })

        VolleyService.getRequestQueue(context!!).add(leaderboardRequest)
        /*
        leaderboard_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = LeaderboardAdapter(teams)
            addItemDecoration(ItemDividerDecoration(context, 32, 32))
        }
        */
    }
}
