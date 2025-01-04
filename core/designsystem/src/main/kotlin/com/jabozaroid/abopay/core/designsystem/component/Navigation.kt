package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Int,
    modifier: Modifier = Modifier,
    selectedIcon: Int = icon,
    enabled: Boolean = true,
    label: String? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = AppTheme.colorScheme.secondary,
        unselectedIconColor = AppTheme.colorScheme.outline,
        selectedTextColor = AppTheme.colorScheme.secondary,
        unselectedTextColor = AppTheme.colorScheme.outline,
        indicatorColor = AppTheme.colorScheme.background,

        ),
) {

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected)
                Icon(
                    modifier = Modifier.size(30.dp, 30.dp),
                    painter = painterResource(id = selectedIcon),
                    contentDescription = "selected icon $selectedIcon"
                )
            else
                Icon(
                    modifier = Modifier.size(Dimens.size_25, Dimens.size_25),
                    painter = painterResource(id = icon),
                    contentDescription = "selected icon $icon"
                )
        },
        modifier = modifier,
        enabled = enabled,
        label = {
            if (label != null) ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                Text(label)
            }
        },
        alwaysShowLabel = alwaysShowLabel,
        colors = colors
    )

}

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colorScheme.background,
    contentColor: Color = AppTheme.colorScheme.secondary,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = Dimens.size_10,
                    topEnd = Dimens.size_10
                )
            ),
        contentColor = contentColor,
        containerColor = containerColor,
        content = content,
        windowInsets = WindowInsets(0.dp)
    )
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = AppTheme.colorScheme.background,
        header = header,
        content = content
    )
}

@ThemePreviews
@Composable
fun AppNavigationPreview() {
    val items = listOf("Home", "Profile", "Setting")
    val icons = listOf(
        R.drawable.ic_list_bullet,
        R.drawable.ic_edit_item,
        R.drawable.ic_setting,
    )
    val selectedIcons = listOf(
        R.drawable.ic_list_bullet,
        R.drawable.ic_edit_item,
        R.drawable.ic_setting,
    )

    AppTheme {
        AppNavigationBar(
            modifier = Modifier
                .height(50.dp)
        ) {
            items.forEachIndexed { index, item ->
                AppNavigationBarItem(
                    modifier = Modifier.height(Dimens.size_15),
                    icon = icons[index],
                    selectedIcon = selectedIcons[index],
                    label = items[index],
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}
