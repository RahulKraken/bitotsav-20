package `in`.bitotsav.bitotsav_20.db

import `in`.bitotsav.bitotsav_20.event.data.EventDao
import `in`.bitotsav.bitotsav_20.event.data.WinnerDao
import `in`.bitotsav.bitotsav_20.event.data.Event
import `in`.bitotsav.bitotsav_20.profile.data.User
import `in`.bitotsav.bitotsav_20.event.data.Winner
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.feed.data.FeedDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Event::class, Winner::class, User::class, Feed::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao() : EventDao
    abstract fun winnerDao() : WinnerDao
    abstract fun feedDao() : FeedDao

    companion object {
        private const val TAG = "EventDatabase"

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
        ).build()
    }
}