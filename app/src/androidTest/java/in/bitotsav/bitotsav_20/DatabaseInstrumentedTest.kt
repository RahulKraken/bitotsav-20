package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.dao.EventDao
import `in`.bitotsav.bitotsav_20.dao.WinnerDao
import `in`.bitotsav.bitotsav_20.db.EventDatabase
import `in`.bitotsav.bitotsav_20.entity.Event
import `in`.bitotsav.bitotsav_20.entity.Winner
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {
    private lateinit var eventDao: EventDao
    private lateinit var winnerDao: WinnerDao

    private lateinit var db: EventDatabase
    private lateinit var context: Context

    @Before
    fun createDb() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, EventDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        eventDao = db.eventDao()
        winnerDao = db.winnerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun runEventTest() = runBlocking {
        val event = Event(
            1,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "1",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        )
        eventDao.insert(event)
        val e = eventDao.getEvent(1)
        assertEquals(e?.id, 1)
    }

    @Test
    @Throws(Exception::class)
    fun runAllEventsTest() = runBlocking {
        val event1 = Event(
            1, "event", "desc", "status", "timing", "venue", "1", "2", "coordinators", "category", "tag", "duration"
        )
        val event2 = Event(
            2, "event", "desc", "status", "timing", "venue", "1", "2", "coordinators", "category", "tag", "duration"
        )
        val event3 = Event(
            3, "event", "desc", "status", "timing", "venue", "2", "2", "coordinators", "category", "tag", "duration"
        )
        val event4 = Event(
            4, "event", "desc", "status", "timing", "venue", "2", "2", "coordinators", "category", "tag", "duration"
        )

        eventDao.insert(event1)
        eventDao.insert(event2)
        eventDao.insert(event3)
        eventDao.insert(event4)

        val events = eventDao.getAllEvents()

        var i = 1
        for (e in events) {
            assertEquals(e.id, i)
            i++
        }
    }

    @Test
    @Throws(Exception::class)
    fun runEventForDayTest() = runBlocking {

        val event1 = Event(
            1, "event", "desc", "status", "timing", "venue", "1", "2", "coordinators", "category", "tag", "duration"
        )
        val event2 = Event(
            2, "event", "desc", "status", "timing", "venue", "1", "2", "coordinators", "category", "tag", "duration"
        )
        val event3 = Event(
            3, "event", "desc", "status", "timing", "venue", "2", "2", "coordinators", "category", "tag", "duration"
        )
        val event4 = Event(
            4, "event", "desc", "status", "timing", "venue", "2", "2", "coordinators", "category", "tag", "duration"
        )

        eventDao.insert(event1)
        eventDao.insert(event2)
        eventDao.insert(event3)
        eventDao.insert(event4)

        val dayEvents = eventDao.getEventsForDay("2")

        for (e in dayEvents) {
            assertEquals(e.day, "2")
        }
    }

    @Test
    @Throws(Exception::class)
    fun runWinnerTest() = runBlocking {
        val event = Event(
            2,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "1",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        )
        eventDao.insert(event)
        val winner = Winner(2, "first", "second", "third")
        winnerDao.insert(winner)
        val w = winnerDao.getWinners(2)
        assertEquals(w?.eventId, 2)
    }
}
