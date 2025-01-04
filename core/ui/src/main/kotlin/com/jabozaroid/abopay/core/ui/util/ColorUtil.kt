package com.jabozaroid.abopay.core.ui.util

import androidx.compose.ui.graphics.Color

object ColorUtil {

    fun String.asColor(): Color {
        return Color(android.graphics.Color.parseColor(this))
    }

}