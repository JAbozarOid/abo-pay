package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun AmountGridComponent(
    modifier: Modifier = Modifier,
    items: List<AmountUiModel>,
    onItemClick: (AmountUiModel) -> Unit
) {

    var selectedItem by remember {
        mutableStateOf<AmountUiModel?>(null)
    }

    CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
            contentPadding = PaddingValues(
                vertical = Dimens.size_4,
            ),

            modifier = modifier
                .background(AppTheme.colorScheme.background)
                .fillMaxWidth()
        ) {
            items(
                count = items.size,
            ) { item ->
                if (item == 0)
                    Spacer(modifier = Modifier.width(Dimens.size_4))
                IconItem(
                    modifier = Modifier,
                    onClick = {
                        selectedItem = items[item]
                        onItemClick(items[item])
                    },
                    item = items[item],

                    isSelected = items[item] == selectedItem
                )
            }

        }
    }

}

@Composable
private fun IconItem(
    modifier: Modifier,
    item: AmountUiModel,
    onClick: () -> Unit,
    isSelected: Boolean,
) {

    Card(
        modifier = modifier
            .size(Dimens.size_100, Dimens.size_40)
            .padding(Dimens.size_4)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.ivaWhiteBackground),
        border = BorderStroke(Dimens.size_1, Color.Black),
    ) {
        if (isSelected) Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                Text(
                    text = item.amount, fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1

                )
            }
        }
        else Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                Text(
                    text = item.amount, fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1

                )
            }
        }

    }


}

@Preview(showBackground = true)
@ThemePreviews
@Composable
fun PreviewAmountGridComponent() {

    AppTheme {
        Column(
            modifier = Modifier.padding(Dimens.size_10)
        ) {
            AmountGridComponent(
                items = listOf(
                    AmountUiModel(amount = "50.000"),
                    AmountUiModel(amount = "100.000"),
                    AmountUiModel(amount = "200.000"),
                    AmountUiModel(amount = "20.00"),
                    AmountUiModel(amount = "300.000"),
                    AmountUiModel(amount = "5.000"),
                    AmountUiModel(amount = "10.0"),
                ), onItemClick = {})
        }


    }

}
