package `in`.bitotsav.bitotsav_20.viewModelFactory

import `in`.bitotsav.bitotsav_20.event.data.EventRepository
import `in`.bitotsav.bitotsav_20.viewModel.MainActivityViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class MainActivityViewModelFactory (
    private val dataRepository: EventRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                dataRepository,
                application
            ) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}