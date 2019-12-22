package `in`.bitotsav.bitotsav_20.event.ui

import `in`.bitotsav.bitotsav_20.event.data.EventRepository
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class ScheduleViewModelFactory (
    private val dataRepository: EventRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(
                dataRepository,
                application
            ) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}