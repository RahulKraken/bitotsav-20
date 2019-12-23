package `in`.bitotsav.bitotsav_20.event.ui

import `in`.bitotsav.bitotsav_20.event.data.Event
import `in`.bitotsav.bitotsav_20.event.data.EventRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel(
    private val repository: EventRepository,
    application: Application
) : AndroidViewModel(application) {

    private var scheduleJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + scheduleJob)

    private var event = MutableLiveData<Event>()
    private var allEvents = repository.getAllEvents()
    private var allEventsForDay = repository.getAllEventsForDay("1")
    private var allFlagshipEvents = repository.getAllFlagshipEvents()

    init {
        println("viewModel init: ${allEvents.value}")
        println("viewModel init: ${allEventsForDay.value}")
        println("viewModel init: ${allFlagshipEvents.value}")
        initializeEvents()
    }

    private fun initializeEvents() {
        uiScope.launch {
            allEvents = repository.getAllEvents()
            allEventsForDay= repository.getAllEventsForDay("1")
            event.value = getEventAsync(1)
            allFlagshipEvents = repository.getAllFlagshipEvents()
        }
    }

    /**
     * add event @param "event" to DB
     */
    fun addEvent(event: Event) {
        uiScope.launch {
            repository.insertEvent(event)
            println("INSERTED EVENT: $event")
        }
    }

    /*
    private suspend fun addEventAsync(event: Event) {
        repository.insertEvent(event)
        println("INSERTED EVENT: $event")
    }
     */

    /**
     * update event @param "event"
     */
    fun updateEvent(event: Event) {
        uiScope.launch {
            repository.updateEvent(event)
            println("UPDATED EVENT: $event")
        }
    }

    /**
     * delete event @param "event"
     */
    fun deleteEvent(event: Event) {
        uiScope.launch {
            repository.deleteEvent(event)
            println("DELETED EVENT: $event")
        }
    }

    /**
     * delete all events
     */
    fun deleteAllEvents() {
        uiScope.launch {
            repository.deleteAllEvents()
            println("DELETED ALL EVENTS")
        }
    }

    /**
     * fetch event from DB with id @param: "id"
     */
    fun getEvent(id: Int): MutableLiveData<Event> {
        uiScope.launch {
            event.value = getEventAsync(id)
        }
        return event
    }

    private suspend fun getEventAsync(id: Int): Event? {
        return withContext(Dispatchers.IO) {
            repository.getEvent(id)
        }
    }

    /**
     * get all events
     */
    fun getAllEvents(): LiveData<List<Event>> {
        return allEvents
    }
/*
    private suspend fun getAllEventsAsync(): List<Event> {
        return withContext(Dispatchers.IO) {
            repository.getAllEvents()
        }
    }*/

    /**
     * get all events for day @param "day"
     */
    fun getAllEventsForDay(day: String): LiveData<List<Event>> {
        uiScope.launch {
            allEventsForDay = getAllEventsForDayAsync(day)
        }
        println("viewModel: ${allEventsForDay.value}")
        return allEventsForDay
    }

    private suspend fun getAllEventsForDayAsync(day: String): LiveData<List<Event>> {
        return withContext(Dispatchers.IO) {
            repository.getAllEventsForDay(day)
        }
    }

    fun getAllFlagshipEvents(): LiveData<List<Event>> {
        allFlagshipEvents = repository.getAllFlagshipEvents()
        return allFlagshipEvents
    }
/*
    private suspend fun getAllFlagshipEventsAsync(): List<Event> {
        return withContext(Dispatchers.IO) {
            repository.getAllFlagshipEvents()
        }
    }*/

/*
    fun insertEvent(event: Event) {
        addEvent(event)
    }

    fun getEvent(id: Int): MutableLiveData<Event> {
        getEventFromDb(1)
        return event
    }
*/

    override fun onCleared() {
        super.onCleared()
        scheduleJob.cancel()
    }
}