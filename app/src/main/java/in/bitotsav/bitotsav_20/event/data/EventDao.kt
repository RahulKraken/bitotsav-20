package `in`.bitotsav.bitotsav_20.event.data

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

    @Query("SELECT * FROM events")
    suspend fun getAllEvents() : List<Event>

    @Query("SELECT * FROM events WHERE day = :day")
    suspend fun getEventsForDay(day: String) : List<Event>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEvent(id: Int) : Event?

    @Query("SELECT * FROM events WHERE tag = 'flagship'")
    suspend fun getAllFlagshipEvents() : List<Event>
}