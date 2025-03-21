package com.jabozaroid.abopay.core.designsystem.theme.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A class to model gradient color values for Mark2.
 * @param top The top gradient color to be rendered
 * @param bottom The bottom gradient color to be rendered
 * @param container The container gradient color over which the gradient will be rendered
 */
@Immutable
data class GradientColor(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified,
    val container: Color = Color.Unspecified
)

/**
 * A composition local for [GradientColor]
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColor() }
