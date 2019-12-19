package `in`.bitotsav.bitotsav_20.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "winners",
    foreignKeys = arrayOf(ForeignKey(entity = Event::class, parentColumns = arrayOf("id"), childColumns = arrayOf("event_id"), onDelete = ForeignKey.CASCADE)))
data class Winner(
    @PrimaryKey
    @ColumnInfo(name = "event_id")
    val eventId: String,
    val first: String,
    val second: String,
    val third: String
)