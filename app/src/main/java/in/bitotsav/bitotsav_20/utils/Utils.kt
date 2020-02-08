package `in`.bitotsav.bitotsav_20.utils

import android.content.Context
import android.content.Intent

fun parseDayTime(daytime: String): Pair<Int, String> {
    val parts = daytime.split(":", limit = 2).map { it.trim() }
    val day = when (parts[0]) {
        "14/2" -> 1
        "15/2" -> 2
        "16/2" -> 3
        else -> 1
    }

    val time = parts[1].split("-").map { it.trim() }
    return Pair(day, time[0])
}

fun share(context: Context, subject: String, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    shareIntent.putExtra(Intent.EXTRA_TEXT, message)
    context.startActivity(Intent.createChooser(shareIntent, "Share via..."))
}