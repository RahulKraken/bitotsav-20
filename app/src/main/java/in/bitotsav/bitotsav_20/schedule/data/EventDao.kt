package `in`.bitotsav.bitotsav_20.schedule.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("DELETE FROM events")
    suspend fun deleteAll()

    @Query("SELECT * FROM events ORDER BY timestamp")
    fun getAllEvents() : LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE day LIKE :day ORDER BY timestamp")
    fun getEventsForDay(day: Int) : LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEvent(id: Int) : Event?

    @Query("SELECT * FROM events WHERE category = 'flagship' ORDER BY timestamp")
    fun getAllFlagshipEvents() : LiveData<List<Event>>
}