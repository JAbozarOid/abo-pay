package com.jabozaroid.abopay.core.designsystem.theme.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

//Typography
@Immutable
data class AppTypography(
    val text_8PX_10SP_M: TextStyle,
    val text_8PX_10SP_B: TextStyle,

    val text_9PX_12SP_M: TextStyle,
    val text_9PX_12SP_B: TextStyle,

    val text_10PX_13SP_M: TextStyle,
    val text_10PX_13SP_B: TextStyle,

    val text_11PX_15SP_M: TextStyle,
    val text_11PX_15SP_B: TextStyle,

    val text_12PX_16SP_M: TextStyle,
    val text_12PX_16SP_B: TextStyle,

    val text_13PX_17SP_M: TextStyle,
    val text_13PX_17SP_B: TextStyle,

    val text_14PX_19SP_M: TextStyle,
    val text_14PX_19SP_B: TextStyle,

    val text_15PX_20SP_M: TextStyle,
    val text_15PX_20SP_B: TextStyle,

    val text_16PX_21SP_B: TextStyle,
    val text_16PX_21SP_M: TextStyle,
)

/**
 * A composition local for [AppTypography]
 */
val LocalAppTypographyScheme = staticCompositionLocalOf {
    AppTypography(
        text_8PX_10SP_M = TextStyle.Default,
        text_8PX_10SP_B = TextStyle.Default,

        text_9PX_12SP_M = TextStyle.Default,
        text_9PX_12SP_B = TextStyle.Default,

        text_10PX_13SP_M = TextStyle.Default,
        text_10PX_13SP_B = TextStyle.Default,

        text_11PX_15SP_M = TextStyle.Default,
        text_11PX_15SP_B = TextStyle.Default,

        text_12PX_16SP_M = TextStyle.Default,
        text_12PX_16SP_B = TextStyle.Default,

        text_13PX_17SP_M = TextStyle.Default,
        text_13PX_17SP_B = TextStyle.Default,

        text_14PX_19SP_M = TextStyle.Default,
        text_14PX_19SP_B = TextStyle.Default,

        text_15PX_20SP_M = TextStyle.Default,
        text_15PX_20SP_B = TextStyle.Default,

        text_16PX_21SP_M = TextStyle.Default,
        text_16PX_21SP_B = TextStyle.Default,
    )
}