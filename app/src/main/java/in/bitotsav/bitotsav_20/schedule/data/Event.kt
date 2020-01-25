package `in`.bitotsav.bitotsav_20.schedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "event_name")
    val name: String,
    val description: String,
    @ColumnInfo(name = "rules")
    val rulesAndRegulations: String,
    val minParticipants: Int,
    val maxParticipants: Int,
    val individual: String,
    val imageName: String,
    val status: String,
    val timing: String,
    val venue: String,
    val day: Int,
    @ColumnInfo(name = "team_size")
    val teamSize: String,
    val contactInformation: String,
    val eventCategory: String,
    val category: String,
    val cashPrize: String,
    val duration: String,
    var isStarred: Boolean,
    val points: String
) : Serializable {
    @Expose(serialize = false, deserialize = false)
    var timestamp = getTimestampFromString(day, timing)

    fun setProperties(isStarred: Boolean) {
        this.isStarred = isStarred
        this.timestamp = getTimestampFromString(day, timing)
    }

    private fun getTimestampFromString(day: Int, timeString: String): Long {
        val (hours, minutes) = timeString.split(":").map { it.toInt() }
        val timestamp = GregorianCalendar(TimeZone.getTimeZone("Asia/Kolkata"))
        timestamp.set(2020, 1, day + 14, hours, minutes)
        return timestamp.timeInMillis
    }
}