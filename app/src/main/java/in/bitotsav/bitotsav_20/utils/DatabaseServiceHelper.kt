package `in`.bitotsav.bitotsav_20.utils

import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import `in`.bitotsav.bitotsav_20.schedule.data.EventRepository
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun addToEventDb(context: Context, events: List<Event>) {
    val repo = EventRepository(AppDatabase(context).eventDao())
    CoroutineScope(Dispatchers.IO).launch {
        for (e in events) repo.insertEvent(e)
    }
}