package `in`.bitotsav.bitotsav_20.db

import `in`.bitotsav.bitotsav_20.schedule.data.Event
import `in`.bitotsav.bitotsav_20.schedule.data.EventDao
import `in`.bitotsav.bitotsav_20.schedule.data.Winner
import `in`.bitotsav.bitotsav_20.schedule.data.WinnerDao
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.feed.data.FeedDao
import `in`.bitotsav.bitotsav_20.profile.data.User
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Event::class, Winner::class, Feed::class],
    version = 5,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao() : EventDao
    abstract fun winnerDao() : WinnerDao
    abstract fun feedDao() : FeedDao

    companion object {
        @Volatile private var instance : AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "event_db.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(eventDbCallback)
            .build()

        private val eventDbCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    insertDummyData()
                }
            }
        }

        private suspend fun insertDummyData() {
            val eventDao = instance?.eventDao()
            // events
            var i = 1
            /*for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event one", "a brief description", "not completed", "10:00 AM", "IC Arena", "1", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Formal", "40 min"))
                i++
            }
            for (j in  0 until 4) {
                eventDao?.insert(Event(i, "A decent event one", "a brief description", "not completed", "12:00 PM", "Somewhere else", "1", "2-3", "coordinators with phone separated by comma", "Sports,Action", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event one", "a brief description", "not completed", "3:00 PM", "Sports Complex", "1", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event one", "a brief description", "not completed", "4:00 PM", "Sports Complex", "1", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event two", "a brief description", "not completed", "10:00 AM", "IC Arena", "2", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Formal", "40 min"))
                i++
            }
            for (j in  0 until 4) {
                eventDao?.insert(Event(i, "A decent event two", "a brief description", "not completed", "12:00 PM", "Somewhere else", "2", "2-3", "coordinators with phone separated by comma", "Sports,Action", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event two", "a brief description", "not completed", "3:00 PM", "Sports Complex", "2", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event two", "a brief description", "not completed", "4:00 PM", "Sports Complex", "2", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event three", "a brief description", "not completed", "10:00 AM", "IC Arena", "3", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Formal", "40 min"))
                i++
            }
            for (j in  0 until 4) {
                eventDao?.insert(Event(i, "A decent event three", "a brief description", "not completed", "12:00 PM", "Somewhere else", "3", "2-3", "coordinators with phone separated by comma", "Sports,Action", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event three", "a brief description", "not completed", "3:00 PM", "Sports Complex", "3", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Informal", "40 min"))
                i++
            }
            for (j in 0 until 4) {
                eventDao?.insert(Event(i, "A decent event three", "a brief description", "not completed", "4:00 PM", "Sports Complex", "3", "2-3", "coordinators with phone separated by comma", "Music,Dance", "Informal", "40 min"))
                i++
            }*/

            // feeds
            val feedDao = instance?.feedDao()
            i = 1
            for (j in 0 until 20) {
                feedDao?.insert(Feed(i++, "Very Important announcement", "Some serious detail about the very important announcement", "Announcement", 4578123))
            }
        }
    }
}