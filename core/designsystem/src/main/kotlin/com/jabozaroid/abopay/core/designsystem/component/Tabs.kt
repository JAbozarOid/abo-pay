package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens


@Composable
fun AppTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    title: String
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = {
            val style = AppTheme.typography.text_9PX_12SP_B.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(
                value = style,
                content = {
                    Box(modifier = modifier.padding(top = AppTabDefaults.TabTopPadding)) {
                        Text(
                            color = if (selected) AppTheme.colorScheme.primary else AppTheme.colorScheme.outline,
                            text = title
                        )
                    }
                }
            )
        }
    )

}

@Composable
fun AppTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        indicator = { tabPosition ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPosition[selectedTabIndex]),
                height = Dimens.size_2,
                color = AppTheme.colorScheme.primary
            )
        },
        tabs = tabs
    )
}


@ThemePreviews
@Composable
fun TabsPreview() {
    AppTheme {
        val titles = listOf("Topics", "People")
        AppTabRow(selectedTabIndex = 0) {
            titles.forEachIndexed { index, title ->
                AppTab(
                    selected = index == 0,
                    onClick = { },
                    title = title
                )
            }
        }
    }
}

object AppTabDefaults {
    val TabTopPadding = 7.dp
}
