package com.jabozaroid.abopay.feature.finndow.util

import java.util.concurrent.TimeUnit

class FormatUtil {

    companion object {
        // Helper to format milliseconds into MM:SS
        fun formatTime(ms: Int): String {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(ms.toLong())
            val seconds = TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) % 60
            return String.format("%02d:%02d", minutes, seconds)
        }
    }
}