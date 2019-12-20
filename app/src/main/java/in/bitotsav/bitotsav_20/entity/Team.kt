package `in`.bitotsav.bitotsav_20.entity

import java.io.Serializable

data class Team(
    val teamId: String,
    val teamName: String,
    val teamMembers: String?,
    val rank: Int,
    val points: Int
) : Serializable