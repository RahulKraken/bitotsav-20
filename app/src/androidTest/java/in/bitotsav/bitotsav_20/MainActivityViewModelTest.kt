package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.event.data.EventDao
import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.event.data.Event
import `in`.bitotsav.bitotsav_20.event.data.EventRepository
import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class MainActivityViewModelTest {
    private lateinit var db: AppDatabase
    private lateinit var eventDao: EventDao
    private lateinit var eventRepository: EventRepository
    private lateinit var context: Context

    @Before
    fun init() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        eventDao = db.eventDao()

        runBlocking {
            val event1 = Event(
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
            val event2 = Event(
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
            val event3 = Event(
                3,
                "event",
                "desc",
                "status",
                "timing",
                "venue",
                "2",
                "2",
                "coordinators",
                "category",
                "tag",
                "duration"
            )
            val event4 = Event(
                4,
                "event",
                "desc",
                "status",
                "timing",
                "venue",
                "2",
                "2",
                "coordinators",
                "category",
                "tag",
                "duration"
            )

            eventDao.insert(event1)
            eventDao.insert(event2)
            eventDao.insert(event3)
            eventDao.insert(event4)
        }

        eventRepository =
            EventRepository(eventDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertRetrieveTest() = runBlocking {
        val event5 = Event(
            5,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "2",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        )
        eventRepository.insertEvent(event5)

        val e = eventRepository.getEvent(5)
        assertEquals(e!!.id, 5)
    }

    @Test
    @Throws(Exception::class)
    fun getAllTest() = runBlocking {
        val events = eventRepository.getAllEvents()
        var i = 1
        for (e in events) {
            println("EVENT $i: $e")
            assertEquals(e.id, i)
            i++
        }
    }

    @Test
    @Throws(Exception::class)
    fun getAllForDayTest() = runBlocking {
        val events = eventRepository.getAllEventsForDay("1")
        for (e in events) {
            assertEquals(e.day, "1")
        }
    }
}