package `in`.bitotsav.bitotsav_20.feed.ui

import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.feed.data.FeedRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(
    private val feedRepository: FeedRepository,
    application: Application
) : AndroidViewModel(application) {

    private var feedFragmentJob = Job()

    private var uiScope = CoroutineScope(Dispatchers.Main + feedFragmentJob)
    private var allFeeds = feedRepository.getAll()
    private var feed = MutableLiveData<Feed>()

    init {
        println("feed view model: ${allFeeds.value}")
        initializeFeeds()
    }

    private fun initializeFeeds() {
        uiScope.launch {
            allFeeds = feedRepository.getAll()
            feed.value = getFeedAsync(1)
        }
    }

    /**
     * insert feed
     */
    fun insertFeed(feed: Feed) {
        uiScope.launch {
            insertFeedAsync(feed)
        }
    }

    private suspend fun insertFeedAsync(feed: Feed) {
        withContext(Dispatchers.IO) {
            feedRepository.insertFeed(feed)
        }
    }

    /**
     * update feed
     */
    fun updateFeed(feed: Feed) {
        uiScope.launch {
            updateFeedAsync(feed)
        }
    }

    private suspend fun updateFeedAsync(feed: Feed) {
        withContext(Dispatchers.IO) {
            feedRepository.updateFeed(feed)
        }
    }

    /**
     * delete feed
     */
    fun deleteFeed(feed: Feed) {
        uiScope.launch {
            deleteFeedAsync(feed)
        }
    }

    private suspend fun deleteFeedAsync(feed: Feed) {
        withContext(Dispatchers.IO) {
            feedRepository.deleteFeed(feed)
        }
    }

    /**
     * delete all feeds
     */
    fun deleteAll() {
        uiScope.launch {
            deleteAllAsync()
        }
    }

    private suspend fun deleteAllAsync() {
        withContext(Dispatchers.IO) {
            feedRepository.deleteAllFeeds()
        }
    }

    /**
     * get feed with id @param "id"
     */
    fun getFeed(id: Int): MutableLiveData<Feed> {
        uiScope.launch {
            feed.value = getFeedAsync(id)
        }
        return feed
    }

    private suspend fun getFeedAsync(id: Int): Feed? {
        return withContext(Dispatchers.IO) {
            feedRepository.getFeed(id)
        }
    }

    /**
     * get all feeds
     */
    fun getAllFeeds(): LiveData<List<Feed>> {
        return allFeeds
    }
}