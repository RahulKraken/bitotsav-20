package `in`.bitotsav.bitotsav_20.feed.data

import androidx.annotation.WorkerThread

class FeedRepository(private val feedDao: FeedDao) {

    @WorkerThread
    suspend fun insertFeed(feed: Feed) = feedDao.insert(feed)

    @WorkerThread
    suspend fun updateFeed(feed: Feed) = feedDao.update(feed)

    @WorkerThread
    suspend fun deleteFeed(feed: Feed) = feedDao.delete(feed)

    @WorkerThread
    suspend fun deleteAllFeeds() = feedDao.deleteAll()

    @WorkerThread
    fun getAll() = feedDao.getAll()

    @WorkerThread
    suspend fun getFeed(id: Long) = feedDao.get(id)
}