package `in`.bitotsav.bitotsav_20.utils

import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import com.google.gson.Gson

object GsonUtils {
    private val gson = Gson()

    fun deserializeUser(user: String): User {
        return gson.fromJson(user, User::class.java)
    }

    fun serializeUser(user: User): String {
        return gson.toJson(user).toString()
    }

    fun deserializeEvent(event: String): Event {
        return gson.fromJson(event, Event::class.java)
    }

    fun serializeEvent(event: Event): String {
        return gson.toJson(event)
    }

    fun deserializeEventList(events: String): List<Event> {
        return gson.fromJson(events, Array<Event>::class.java).asList()
    }

    fun serializeEventList(events: String): String {
        return gson.toJson(events)
    }
}