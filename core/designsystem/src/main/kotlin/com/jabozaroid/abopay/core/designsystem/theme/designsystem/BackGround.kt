package com.jabozaroid.abopay.core.designsystem.theme.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Immutable
data class AppBackground(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
)
/**
 * A composition local for [AppBackground]
 */
val LocalAppBackground = staticCompositionLocalOf {
    AppBackground(
        color = Color.Unspecified,
        tonalElevation = Dp.Unspecified
    )
}
