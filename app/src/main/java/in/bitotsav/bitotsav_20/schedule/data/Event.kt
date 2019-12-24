package `in`.bitotsav.bitotsav_20.schedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "event_name")
    val eventName: String,
    val description: String,
    val status: String,
    val timing: String,
    val venue: String,
    val day: String,
    @ColumnInfo(name = "team_size")
    val teamSize: String,
    val coordinators: String,
    val categories: String,
    val tag: String,
    val duration: String
) : Serializable