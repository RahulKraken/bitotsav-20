package `in`.bitotsav.bitotsav_20.schedule.api

import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import `in`.bitotsav.bitotsav_20.schedule.data.EventRepository
import `in`.bitotsav.bitotsav_20.schedule.ui.ScheduleViewModel
import `in`.bitotsav.bitotsav_20.schedule.ui.ScheduleViewModelFactory
import `in`.bitotsav.bitotsav_20.utils.GsonUtils
import `in`.bitotsav.bitotsav_20.utils.addToEventDb
import `in`.bitotsav.bitotsav_20.utils.parseDayTime
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

val BASE_URL = "https://bitotsav.in"

fun getAllEvents(context: Context) {
    var events = ArrayList<Event>()
    val req = StringRequest(Request.Method.GET, BASE_URL + "/api/events/allEvents", Response.Listener {res ->
        val obj = JSONObject(res)
        val arr = obj.getJSONArray("events")
        for (i in 0 until arr.length()) {
            val event = GsonUtils.deserializeEvent(arr.getString(i))
            @Suppress("SENSELESS_COMPARISON")
            val daytime = parseDayTime(event.duration!!)
            if (event.day == null) event.day = daytime.first
            if (event.timing == null) event.timing = daytime.second
            events.add(event)
        }
        addToEventDb(context, events)
    }, Response.ErrorListener { err ->
        println("ERR: $err")
    })
    VolleyService.getRequestQueue(context).add(req)
}
