package `in`.bitotsav.bitotsav_20.schedule.api

import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import `in`.bitotsav.bitotsav_20.utils.GsonUtils
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

val BASE_URL = "https://bitotsav.in"

fun getAllEvents(context: Context): LiveData<List<Event>> {
    val events = MutableLiveData<List<Event>>()
    val req = StringRequest(Request.Method.GET, BASE_URL + "/api/events/allEvents", Response.Listener {res ->
        println("200 OK: $res")
        val obj = JSONObject(res)
        val arr = obj.getJSONArray("events")
        for (i in 0 until 5) println(GsonUtils.deserializeEvent(arr.getJSONObject(i).toString()))
    }, Response.ErrorListener { err ->
        println("ERR: $err")
    })
    VolleyService.getRequestQueue(context).add(req)
    return events
}
