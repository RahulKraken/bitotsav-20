package `in`.bitotsav.bitotsav_20.feed.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FeedDao {

    @Insert
    suspend fun insert(feed: Feed)

    @Update
    suspend fun update(feed: Feed)

    @Delete
    suspend fun delete(feed: Feed)

    @Query("DELETE FROM feed")
    suspend fun deleteAll()

    @Query("SELECT * FROM feed ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<Feed>>

    @Query("SELECT * FROM feed WHERE id = :id LIMIT 1")
    suspend fun get(id: Int): Feed?
}