package `in`.bitotsav.bitotsav_20.feed.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed")
data class Feed(
    @PrimaryKey
    val id: Int,
    val title: String,
    val message: String,
    val type: String,
    val timestamp: Long
)