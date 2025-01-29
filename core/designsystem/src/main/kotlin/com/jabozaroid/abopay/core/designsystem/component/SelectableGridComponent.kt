package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun SelectableGridComponent(
    modifier: Modifier,
    containerColor: Color = AppTheme.colorScheme.background,
    items: List<IconItemUiModel>,
    onItemClicked: (iconItem: IconItemUiModel) -> Unit,
    isSelected: Boolean = false,
    selectedIndex: Int? = null,
) {

    var selectedItem by remember {
        mutableStateOf<IconItemUiModel?>(null)
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        )

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 4),
                    contentPadding = PaddingValues(
                        vertical = Dimens.size_8,
                        horizontal = Dimens.size_16
                    ),

                    modifier = Modifier
                        .background(AppTheme.colorScheme.background)
                        .fillMaxWidth()
                ) {

                    items(
                        items = items,
                        key = { item -> item.index }
                    ) { item ->
                        if (!isSelected) selectedItem = null
                        if (item.index == 0)
                            Spacer(modifier = Modifier.width(Dimens.size_4))
                        IconItem(
                            modifier = Modifier,
                            onClick = {
                                onItemClicked(item)
                                selectedItem = item
                            },
                            item = item,

                            isSelected = item == selectedItem || selectedIndex?.let {
                                item == items[it]
                            } ?: false
                        )
                    }

                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.size_16),
                    thickness = Dimens.size_1,
                    color = AppTheme.colorScheme.aboLine,
                )
            }
        }
    }
}

@Composable
private fun IconItem(
    modifier: Modifier,
    item: IconItemUiModel,
    onClick: () -> Unit,
    isSelected: Boolean,
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val itemSize = (screenWidth / 4) - 20
    Card(
        modifier = modifier
            .size(itemSize.dp, itemSize.dp)
            .padding(Dimens.size_4)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundButton2),
            contentAlignment = Alignment.Center,
        ) {
            UnsafeImageApp(
                darkLogo = item.darkLogo,
                lightLogo = item.lightLogo,
                placeholder = R.drawable.ic_feature,
                modifier = Modifier.size(Dimens.size_40, Dimens.size_40),
                contentDescription = "Item",
                colorFilter = if (!isSelected) ColorFilter.colorMatrix(ColorMatrix().apply {
                    setToSaturation(
                        0f
                    )
                }) else null
            )
        }
    }


}

@Preview(showBackground = true)
@ThemePreviews
@Composable
fun PreviewSelectableGrid() {

    AppTheme {

        val items = listOf(
            IconItemUiModel(
                index = 0,
                title = "کارت به کارت"
            ),
            IconItemUiModel(
                index = 1,
                title = "خرید بیمه",
            ),
            IconItemUiModel(
                index = 2,
                title = "نشان بانک",
            ),
            IconItemUiModel(
                index = 3,
                title = "عوارض خروج",
            ),
            IconItemUiModel(
                index = 4,
                title = "اینترنت",
            ),
            IconItemUiModel(
                index = 5,
                title = "شارژ",
            ),
            IconItemUiModel(
                index = 6,
                title = "موجودی",
            ),
        )

        Column(
            modifier = Modifier.padding(Dimens.size_10)
        ) {
            SelectableGridComponent(
                modifier = Modifier.fillMaxWidth(),
                items = items, onItemClicked = {}, selectedIndex = null
            )
        }

    }

}
