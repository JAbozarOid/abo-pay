package com.jabozaroid.abopay.core.designsystem.theme.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

//Shape
@Immutable
data class AppShape(
    val container: Shape,
    val button: Shape,
)

/**
 * A composition local for [AppShape]
 */
val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

