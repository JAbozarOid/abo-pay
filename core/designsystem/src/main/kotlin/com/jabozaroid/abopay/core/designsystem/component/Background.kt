package com.jabozaroid.abopay.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.LocalAppBackground

/**
 * The main background of the app.
 * Uses [LocalAppBackground] to set the color and tonal elevation of a [Surface].
 *
 * @param modifier Modifier to be applied to the background
 * @param content The background content.
 */
@Composable
fun AppBackground(modifier: Modifier, content: @Composable () -> Unit) {
    val color = LocalAppBackground.current.color
    val tonalElevation = LocalAppBackground.current.tonalElevation
    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        tonalElevation = if (tonalElevation == Dp.Unspecified) Dimens.size_0 else tonalElevation,
        modifier = modifier.fillMaxSize()
    ) {
        CompositionLocalProvider(
            LocalAbsoluteTonalElevation provides Dimens.size_0
        ) {
            content()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews


@ThemePreviews
@Composable
fun PreviewDefault() {
    AppTheme {
        AppBackground(modifier = Modifier.size(Dimens.size_100), content = {})
    }
}
