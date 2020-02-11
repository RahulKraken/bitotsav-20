package `in`.bitotsav.bitotsav_20.profile.data

import java.io.Serializable

data class TeamMember (
    val name: String,
    val bitotsavId: Int
)

data class User(
    val bitotsavId: Int? = -1,
    val name: String? = null,
    val email: String,
    val phoneNo: String,
    val gender: Int? = null,
    val clgName: String? = null,
    val clgCity: String? = null,
    val clgState: String? = null,
    val clgId: String? = null,
    val isVerified: Boolean? = false,
    val isInTeam: Boolean,
    var teamName: String? = null,
    var teamId: Int? = null,
    var teamMembers: List<TeamMember>? = null
//    var score: Int
//    val day1: Boolean = false,
//    val day2: Boolean = false,
//    val day3: Boolean = false,
//    val merchandise: Boolean = false,
//    val accommodation: Boolean = false
) : Serializable