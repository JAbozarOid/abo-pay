package com.jabozaroid.abopay.core.common.util

import java.time.LocalTime
import java.util.Locale

object TimeUtil {
    fun hourAsInt(hour: String): Int {
        val parsedTime = LocalTime.parse("$hour:00:00")
        return parsedTime.hour
    }

    fun minutesAsInt(minutes: String): Int {
        val parsedTime = LocalTime.parse("00:$minutes:00")
        return parsedTime.minute
    }

    fun toDualTimeFormat(time: Long): String {

        var second = (time / 1000)
        val minutes = (second / 60)
        second %= 60
        return "${String.format(Locale.US, "%02d", minutes)}:${String.format(Locale.US, "%02d", second)}"

    }

}