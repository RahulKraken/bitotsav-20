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
    var rulesAndRegulations: String?,
    var minParticipants: Int?,
    var maxParticipants: Int?,
    var individual: Int?,
    var imageName: String?,
    var status: String?,
    var timing: String,
    var venue: String,
    var day: Int?,
    @ColumnInfo(name = "team_size")
    var teamSize: String?,
    var contactInformation: String?,
    var eventCategory: String?,
    var category: String?,
    var cashPrize: String?,
    var duration: String?,
    var isStarred: Boolean?,
    var points: String?
) : Serializable {
    @Expose(serialize = false, deserialize = false)
    var timestamp = getTimestampFromString(day, timing)

    fun setProperties(isStarred: Boolean) {
        this.isStarred = isStarred
        this.timestamp = getTimestampFromString(day, timing)
    }

    private fun getTimestampFromString(day: Int?, timeString: String?): Long {
        if (day == null || timeString == null || !timeString.matches("((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))".toRegex())) {
            println("$day: timeInMillis error")
            this.day = 50
            return 0L
        }
        var (hours, minutes) = timeString.split(" ")[0].split(":").map { it.toInt() }
        if (timeString.split(" ")[1].contentEquals("PM")) hours += 12
        val timestamp = GregorianCalendar(TimeZone.getTimeZone("Asia/Kolkata"))
        timestamp.set(2020, 1, day + 14, hours, minutes)
        println("id: $id, day: $day, timeString: $timeString, timestamp: ${timestamp.timeInMillis}")
        return timestamp.timeInMillis
    }
}