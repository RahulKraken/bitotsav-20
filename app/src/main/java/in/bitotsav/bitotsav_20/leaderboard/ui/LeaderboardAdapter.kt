package `in`.bitotsav.bitotsav_20.leaderboard.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.leaderboard.data.Team
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter (private val teams: List<Team>) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LeaderboardViewHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    class LeaderboardViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_leaderboard, parent, false)) {

        private var avatar: ImageView = itemView.findViewById(R.id.item_avatar)
        private var rank: TextView = itemView.findViewById(R.id.item_rank)
        private var teamName: TextView = itemView.findViewById(R.id.item_name)
        private var points: TextView = itemView.findViewById(R.id.item_points)

        fun bind(team: Team) {
            rank.text = team.rank.toString()
            teamName.text = team.teamName
            points.text = team.points.toString()
        }
    }
}