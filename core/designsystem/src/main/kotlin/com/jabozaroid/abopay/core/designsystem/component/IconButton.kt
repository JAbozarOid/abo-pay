package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

@Composable
fun AppIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(
        checkedContainerColor = AppTheme.colorScheme.primary,
        checkedContentColor = AppTheme.colorScheme.onPrimary,
        disabledContainerColor = if (checked) {
            AppTheme.colorScheme.onBackground.copy(
                alpha = MarkIconButtonDefaults.DisabledIconButtonContainerAlpha
            )
        } else Color.Transparent,

        ),
) {

    FilledIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors
    ) {
        if (checked) checkedIcon() else icon()
    }

}

@ThemePreviews
@Composable
fun IconButtonPreview() {
    AppTheme {
        AppIconToggleButton(
            checked = true,
            onCheckedChange = { },
            icon = {
                Icon(
                    imageVector = AppIcons.BookmarkBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = AppIcons.Bookmark,
                    contentDescription = null,
                )
            },
        )
    }
}

@ThemePreviews
@Composable
fun IconButtonPreviewUnchecked() {
    AppTheme {
        AppIconToggleButton(
            checked = false,
            onCheckedChange = { },
            icon = {
                Icon(
                    imageVector = AppIcons.BookmarkBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = AppIcons.Bookmark,
                    contentDescription = null,
                )
            },
        )
    }
}


/**
 * Mark icon button default values.
 */
object MarkIconButtonDefaults {
    const val DisabledIconButtonContainerAlpha = 0.12f
}