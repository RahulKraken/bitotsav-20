package `in`.bitotsav.bitotsav_20.dao

import `in`.bitotsav.bitotsav_20.entity.Winner
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WinnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(winner: Winner)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(winner: Winner)

    @Delete
    fun delete(winner: Winner)

    @Query("DELETE FROM winners")
    fun deleteAll()

    @Query("SELECT * FROM winners WHERE event_id = :id")
    fun getWinners(id: Int) : LiveData<Winner>
}