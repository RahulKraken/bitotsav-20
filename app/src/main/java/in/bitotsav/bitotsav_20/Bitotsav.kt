package `in`.bitotsav.bitotsav_20

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class Bitotsav : Application() {
    lateinit var requestQueue: RequestQueue

    override fun onCreate() {
        super.onCreate()
        requestQueue = Volley.newRequestQueue(applicationContext)
    }
}