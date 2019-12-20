package `in`.bitotsav.bitotsav_20.event.data

import `in`.bitotsav.bitotsav_20.event.data.Winner
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WinnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(winner: Winner)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(winner: Winner)

    @Delete
    suspend fun delete(winner: Winner)

    @Query("DELETE FROM winners")
    suspend fun deleteAll()

    @Query("SELECT * FROM winners WHERE event_id = :id")
    suspend fun getWinners(id: Int) : Winner?
}