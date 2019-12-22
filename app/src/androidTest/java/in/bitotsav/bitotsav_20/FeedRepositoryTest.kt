package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.feed.data.FeedDao
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class FeedRepositoryTest {
    lateinit var feedDao: FeedDao
    lateinit var db: AppDatabase

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        feedDao = db.feedDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    @Throws(Exception::class)
    fun insertRetrieveTest() = runBlocking {
        val feed = Feed(1, "title", "detail", "type", 32451545)

        feedDao.insert(feed)

        val f = feedDao.get(1)
        assertEquals(f.id, feed.id)
        assertEquals(f.title, feed.title)
        assertEquals(f.timestamp, feed.timestamp)
    }
}