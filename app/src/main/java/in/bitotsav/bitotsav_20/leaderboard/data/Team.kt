package `in`.bitotsav.bitotsav_20.leaderboard.data

import java.io.Serializable

data class Team(
    val teamId: Int,
    val teamName: String,
    val teamMembers: String?,
    val rank: Int,
    val points: Int
) : Serializable