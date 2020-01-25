package `in`.bitotsav.bitotsav_20.utils

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