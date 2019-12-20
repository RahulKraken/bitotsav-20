package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.dao.EventDao
import `in`.bitotsav.bitotsav_20.dao.WinnerDao
import `in`.bitotsav.bitotsav_20.db.EventDatabase
import `in`.bitotsav.bitotsav_20.entity.Event
import `in`.bitotsav.bitotsav_20.entity.Winner
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
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

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

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
    fun runEventTest() {
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
        println(event)
        println(e)
        assertEquals(e?.id, 1)
    }

    @Test
    @Throws(Exception::class)
    fun runWinnerTest() {
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
