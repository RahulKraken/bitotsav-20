package `in`.bitotsav.bitotsav_20.utils

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.profile.data.User
import android.content.Context

class SharedPrefUtils(private val context: Context) {
    private var sharedPref = context.getSharedPreferences(context.getString(R.string.app_shared_preferences), Context.MODE_PRIVATE)

    fun getUser(): User? {
        return if (sharedPref.contains(context.getString(R.string.logged_in_user))) {
            GsonUtils.deserializeUser(sharedPref.getString(context.getString(R.string.logged_in_user), null)!!)
        } else {
            null
        }
    }

    fun setUser(user: User?) {
        if (user == null) {
            sharedPref.edit().remove(context.getString(R.string.logged_in_user)).apply()
        } else {
            val userString = GsonUtils.serializeUser(user)
            sharedPref.edit().putString(context.getString(R.string.logged_in_user), userString).apply()
        }
    }
}