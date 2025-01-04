package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFilterChip(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    label: @Composable () -> Unit
) {

    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = {
            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                label()
            }
        },
        modifier = modifier,
        enabled = enable,
        leadingIcon = if (selected) {
            {
                Icon(imageVector = AppIcons.Check, contentDescription = null)
            }
        } else {
            null
        },
        shape = CircleShape,
        border = FilterChipDefaults.filterChipBorder(
            selected = selected,
            enabled = enable,
            borderColor = AppTheme.colorScheme.outline,
            selectedBorderColor = AppTheme.colorScheme.onBackground,
            disabledBorderColor = AppTheme.colorScheme.outline,
            selectedBorderWidth = AppChipDefaults.ChipBorderWidth
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = AppTheme.colorScheme.onBackground,
            iconColor = AppTheme.colorScheme.onBackground,
            disabledContainerColor = if (selected) {
                AppTheme.colorScheme.onBackground
            } else Color.Transparent,
            disabledLabelColor = AppTheme.colorScheme.onBackground,
            selectedContainerColor = AppTheme.colorScheme.primary,
            selectedLabelColor = AppTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = AppTheme.colorScheme.onPrimary
        )
    )

}

@ThemePreviews
@Composable
fun ChipPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(Dimens.size_80, Dimens.size_100)) {
            Column {
                AppFilterChip(selected = true, onSelectedChange = {}) {
                    Text(text = "Chip")
                }
                AppFilterChip(selected = false, onSelectedChange = {}) {
                    Text(text = "Chips")
                }
            }

        }
    }
}

object AppChipDefaults {
    const val DisabledChipContainerAlpha = 0.12f
    const val DisabledChipContentAlpha = 0.38f
    val ChipBorderWidth = Dimens.size_1
}
