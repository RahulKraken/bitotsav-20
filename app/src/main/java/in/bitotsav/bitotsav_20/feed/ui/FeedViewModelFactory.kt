package `in`.bitotsav.bitotsav_20.feed.ui

import `in`.bitotsav.bitotsav_20.feed.data.FeedRepository
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class FeedViewModelFactory (
    private val dataSource: FeedRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}