package `in`.bitotsav.bitotsav_20.viewModel

import `in`.bitotsav.bitotsav_20.entity.Event
import `in`.bitotsav.bitotsav_20.repository.EventRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val repository: EventRepository,
    application: Application
) : AndroidViewModel(application) {

    private var mainActivityViewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + mainActivityViewModelJob)

    private var event = MutableLiveData<Event>()

    init {
        initializeEvent()
    }

    private fun initializeEvent() {
        uiScope.launch {
            event.value = getEvent()
        }
    }

    private suspend fun getEvent(): Event? {
        return withContext(Dispatchers.IO) {
            repository.getEvent(1)
        }
    }

    private fun addEvent(event: Event) {
        uiScope.launch {
            addEventAsync(event)
        }
    }

    private suspend fun addEventAsync(event: Event) {
        repository.insertEvent(event)
        println("INSERTED EVENT: $event")
    }

    override fun onCleared() {
        super.onCleared()
        mainActivityViewModelJob.cancel()
    }

    fun insertEvent(event: Event) {
        addEvent(event)
    }

    fun getEvent(id: Int): MutableLiveData<Event> {
        return event
    }
}