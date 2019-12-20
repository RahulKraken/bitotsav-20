package `in`.bitotsav.bitotsav_20.dao

import `in`.bitotsav.bitotsav_20.entity.Event
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
    fun insert(event: Event)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("DELETE FROM events")
    fun deleteAll()

    @Query("SELECT * FROM events")
    fun getAllEvents() : LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE day = :day")
    fun getEventsForDay(day: String) : LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEvent(id: Int) : Event?

    @Query("SELECT * FROM events WHERE tag = 'flagship'")
    fun getAllFlagshipEvents() : LiveData<List<Event>>
}