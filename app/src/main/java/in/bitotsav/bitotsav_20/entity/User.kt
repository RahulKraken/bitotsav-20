package `in`.bitotsav.bitotsav_20.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    @ColumnInfo(name = "team_name")
    val teamName: String? = null,
    var score: Int,
    val day1: Boolean = false,
    val day2: Boolean = false,
    val day3: Boolean = false,
    val merchandise: Boolean = false,
    val accommodation: Boolean = false
) : Serializable