package com.jabozaroid.abopay.uikit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.designsystem.component.AmountGridComponent
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppFilterChip
import com.jabozaroid.abopay.core.designsystem.component.AppIconToggleButton
import com.jabozaroid.abopay.core.designsystem.component.AppNavigationBar
import com.jabozaroid.abopay.core.designsystem.component.AppNavigationBarItem
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.AppTab
import com.jabozaroid.abopay.core.designsystem.component.AppTabRow
import com.jabozaroid.abopay.core.designsystem.component.AppTag
import com.jabozaroid.abopay.core.designsystem.component.AppTextButton
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.HorizontalFeatureList
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.FeatureUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.R


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppUiKit() {
    val features = listOf(
        FeatureUiModel(
            "1",
            "کارت به کارت",
        ),
        FeatureUiModel(
            "2",
            "خرید بیمه",
        ),
        FeatureUiModel(
            "3",
            "نشان بانک",
        ),
        FeatureUiModel(
            "4",
            "عوارض خروج",
        ),
        FeatureUiModel(
            "5",
            "اینترنت",
        ),
        FeatureUiModel(
            "6",
            "شارژ",
        ),
        FeatureUiModel(
            "7",
            "موجودی",
        ),
    )
    val items = listOf(
        IconItemUiModel(
            title = "کارت به کارت",
        ),
        IconItemUiModel(
            title = "خرید بیمه",
        ),
        IconItemUiModel(
            title = "نشان بانک",
        ),
        IconItemUiModel(
            title = "عوارض خروج",
        ),
        IconItemUiModel(
            title = "اینترنت",
        ),
        IconItemUiModel(
            title = "شارژ",
        ),
        IconItemUiModel(
            title = "موجودی",
        ),
    )
    AppTheme {
        Surface(color = AppTheme.colorScheme.background2) {
            val contentPadding = WindowInsets
                .systemBars
                .add(
                    WindowInsets(
                        left = Dimens.size_16,
                        top = Dimens.size_16,
                        right = Dimens.size_16,
                        bottom = Dimens.size_16
                    )
                )
                .asPaddingValues()
            AmountGridComponent(
                items = listOf(AmountUiModel("20.000")), onItemClick = {})
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(Dimens.size_16),
            ) {

                item { Text("TopAppBar", Modifier.padding(top = Dimens.size_16)) }
                item {
                    AppToolbar(
                        modifier = Modifier.fillMaxWidth(),
                        toolbarTitle = "ابو پی",
                        onLeftIconClicked = {},
                        onRightIconClicked = {}
                    )
                }
                item {
                    Text(
                        text = "Mark Catalog",
                        style = AppTheme.typography.text_9PX_12SP_M,
                    )
                }
                item { Text("Buttons", Modifier.padding(top = Dimens.size_16)) }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        AppButton(onClick = {}, text = { Text(text = "Enabled") })
                        AppOutlinedButton(onClick = {}) {
                            Text(text = "Enabled")
                        }
                        AppTextButton(onClick = {}) {
                            Text(text = "Enabled")
                        }
                    }
                }
                item { Text("Disabled buttons", Modifier.padding(top = Dimens.size_16)) }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        AppButton(
                            onClick = {},
                            enabled = false,
                            text = {
                                Text(text = "Disabled")
                            }
                        )
                        AppOutlinedButton(
                            onClick = {},
                            enabled = false,
                        ) {
                            Text(text = "Disabled")
                        }
                        AppTextButton(
                            onClick = {},
                            enabled = false,
                        ) {
                            Text(text = "Disabled")
                        }
                    }
                }
                item {
                    Text(
                        "Buttons with leading icons",
                        Modifier.padding(top = Dimens.size_16)
                    )
                }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        AppButton(
                            onClick = {},
                            text = { Text(text = "Enabled") },
                            leadingIcon = {
                                Icon(imageVector = AppIcons.Add, contentDescription = null)
                            },
                        )
                        AppOutlinedButton(
                            onClick = {},
                            text = { Text(text = "Enabled") },
                            leadingIcon = {
                                Icon(imageVector = AppIcons.Add, contentDescription = null)
                            },
                        )
                        AppTextButton(
                            onClick = {},
                            text = { Text(text = "Enabled") },
                            leadingIcon = {
                                Icon(imageVector = AppIcons.Add, contentDescription = null)
                            },
                        )
                    }
                }
                item {
                    Text(
                        "Disabled buttons with leading icons",
                        Modifier.padding(top = Dimens.size_16)
                    )
                }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        AppButton(
                            onClick = {},
                            enabled = false,
                            text = { Text(text = "Disabled") },
                            leadingIcon = {
                                Icon(imageVector = AppIcons.Add, contentDescription = null)
                            },
                        )
                        AppOutlinedButton(
                            onClick = {},
                            enabled = false,
                            text = { Text(text = "Disabled") },
                            leadingIcon = {
                                Icon(imageVector = AppIcons.Add, contentDescription = null)
                            },
                        )
                        AppTextButton(
                            onClick = {},
                            enabled = false,
                            text = { Text(text = "Disabled") },
                            leadingIcon = {
                                Icon(imageVector = AppIcons.Add, contentDescription = null)
                            },
                        )
                    }
                }
                item { Text("Dropdown menus", Modifier.padding(top = Dimens.size_16)) }
                item { Text("Chips", Modifier.padding(top = Dimens.size_16)) }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        var firstChecked by rememberSaveable { mutableStateOf(false) }
                        AppFilterChip(
                            selected = firstChecked,
                            onSelectedChange = { checked -> firstChecked = checked },
                            label = { Text(text = "Enabled") },
                        )
                        var secondChecked by rememberSaveable { mutableStateOf(true) }
                        AppFilterChip(
                            selected = secondChecked,
                            onSelectedChange = { checked -> secondChecked = checked },
                            label = { Text(text = "Enabled") },
                        )
                        AppFilterChip(
                            selected = false,
                            onSelectedChange = {},
                            enable = false,
                            label = { Text(text = "Disabled") },
                        )
                        AppFilterChip(
                            selected = true,
                            onSelectedChange = {},
                            enable = false,
                            label = { Text(text = "Disabled") },
                        )
                    }
                }
                item { Text("Icon buttons", Modifier.padding(top = Dimens.size_16)) }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        var firstChecked by rememberSaveable { mutableStateOf(false) }
                        AppIconToggleButton(
                            checked = firstChecked,
                            onCheckedChange = { checked -> firstChecked = checked },
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
                        var secondChecked by rememberSaveable { mutableStateOf(true) }
                        AppIconToggleButton(
                            checked = secondChecked,
                            onCheckedChange = { checked -> secondChecked = checked },
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
                        AppIconToggleButton(
                            checked = false,
                            onCheckedChange = {},
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
                            enabled = false,
                        )
                        AppIconToggleButton(
                            checked = true,
                            onCheckedChange = {},
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
                            enabled = false,
                        )
                    }
                }
                item { Text("View toggle", Modifier.padding(top = Dimens.size_16)) }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        var firstExpanded by rememberSaveable { mutableStateOf(false) }

                        //View toggle

                    }
                }
                item { Text("Tags", Modifier.padding(top = Dimens.size_16)) }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {
                        AppTag(
                            followed = true,
                            onClick = {},
                            text = { Text(text = "Topic 1".uppercase()) },
                        )
                        AppTag(
                            followed = false,
                            onClick = {},
                            text = { Text(text = "Topic 2".uppercase()) },
                        )
                        AppTag(
                            followed = false,
                            onClick = {},
                            text = { Text(text = "Disabled".uppercase()) },
                            enabled = false,
                        )
                    }
                }
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.size_16)) {

                        HorizontalFeatureList(
                            actionIcon = R.drawable.ic_copy,
                            name = "خدمات پرداخت",
                            modifier = Modifier.fillMaxWidth(),
                            features = features,
                            onFeatureClicked = {})
                    }
                }
                item { Text("Tabs", Modifier.padding(top = Dimens.size_16)) }
                item {
                    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
                    val titles = listOf("Topics", "People")
                    AppTabRow(selectedTabIndex = selectedTabIndex) {
                        titles.forEachIndexed { index, title ->
                            AppTab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                title = title,
                            )
                        }
                    }
                }
                item { Text("Navigation", Modifier.padding(top = Dimens.size_16)) }
                item {
                    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
                    val items = listOf("For you", "Saved", "Interests")
                    val icons = listOf(
                        AppIcons.bottom,
                        AppIcons.bottom,
                        AppIcons.bottom,
                    )
                    val selectedIcons = listOf(
                        AppIcons.Upcoming,
                        AppIcons.Bookmarks,
                        AppIcons.Grid3x3,
                    )
                    AppNavigationBar {
                        items.forEachIndexed { index, item ->
                            AppNavigationBarItem(
                                icon = icons[index],
                                selectedIcon = AppIcons.bottom,
                                label = item,
                                selected = selectedItem == index,
                                onClick = { selectedItem = index },
                            )
                        }
                    }
                }
                item {
                    Text(text = "متن نمونه")
                }
                item {
                    ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_B) {
                        Text(text = "متن نمونه")
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
@ThemePreviews
fun PreviewUiKit() {
    AppUiKit()
}