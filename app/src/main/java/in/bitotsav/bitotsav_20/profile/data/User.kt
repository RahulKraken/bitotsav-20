package `in`.bitotsav.bitotsav_20.profile.data

import java.io.Serializable

data class User(
    val bitotsavId: Int? = -1,
    val name: String,
    val email: String,
    val phoneNo: String,
    val gender: Int,
    val clgName: String,
    val clgCity: String,
    val clgState: String,
    val clgId: String,
    val isVerified: Boolean? = false
//    val teamName: String? = null,
//    var score: Int
//    val day1: Boolean = false,
//    val day2: Boolean = false,
//    val day3: Boolean = false,
//    val merchandise: Boolean = false,
//    val accommodation: Boolean = false
) : Serializable