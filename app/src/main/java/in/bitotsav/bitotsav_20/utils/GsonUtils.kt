package `in`.bitotsav.bitotsav_20.utils

import `in`.bitotsav.bitotsav_20.profile.data.User
import com.google.gson.Gson

object GsonUtils {
    private val gson = Gson()

    fun deserializeUser(user: String): User {
        return gson.fromJson(user, User::class.java)
    }

    fun serializeUser(user: User): String {
        return gson.toJson(user).toString()
    }
}