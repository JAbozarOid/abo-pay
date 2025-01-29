package com.jabozaroid.abopay.core.designsystem.component.cardmanager

import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardplaceholder.CardPlaceHolder
import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import kotlin.math.abs

/**
 *  Created on 10/15/2024 
 **/


@Composable
fun CardList(
    modifier: Modifier = Modifier,
    list: List<Card>,
    onCardSelected: (Card) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val snappingLayout = remember { SnapLayoutInfoProvider(lazyListState) }
    val flingBehavior = rememberSnapFlingBehavior(snapLayoutInfoProvider = snappingLayout)
    val newList = list//.defaultCardMoveToFirstPosition()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        LazyRow(
            modifier = Modifier

                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = if (newList.size == 1) Arrangement.Center else Arrangement.spacedBy(
                (-40).dp
            ),
            state = lazyListState,
            flingBehavior = flingBehavior
        ) {
            itemsIndexed(newList) { index, item ->
                val isSelected =
                    remember {
                        derivedStateOf {

                            val layoutInfo = lazyListState.layoutInfo
                            val visibleItemsInfo = layoutInfo.visibleItemsInfo
                            val itemInfo = visibleItemsInfo.firstOrNull { it.index == index }

                            itemInfo?.let {

                                //First item
                                if (itemInfo.index == 0 && itemInfo.offset == 0)
                                    return@derivedStateOf true

                                //Last item
                                val viewportWidth =
                                    layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
                                if (itemInfo.index + 1 == layoutInfo.totalItemsCount &&
                                    itemInfo.offset + itemInfo.size <= viewportWidth
                                )
                                    return@derivedStateOf true

                                //Other items
                                val delta = 5
                                val center = lazyListState.layoutInfo.viewportEndOffset / 2
                                val childCenter = it.offset + it.size / 2
                                val target = childCenter - center
                                if (target in -delta..delta) return@derivedStateOf true

                            }
                            false
                        }

                    }
                val scale by remember {
                    derivedStateOf {
                        val currentItem =
                            lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                                ?: return@derivedStateOf 1.0f
                        val halfRowWidth = lazyListState.layoutInfo.viewportSize.width / 2
                        (1f - minOf(
                            1f,
                            abs(currentItem.offset + (currentItem.size / 2) - halfRowWidth).toFloat() / halfRowWidth
                        ) * 0.2f)
                    }
                }

                CardPlaceHolder(
                    modifier = Modifier
                        .fillParentMaxWidth(fraction = 0.9f),
                    bankName = item.bankName,
                    cardNumber = item.number,
                    isSelected = isSelected.value,
                    ownerName = item.ownerName,
                    cardColorUp = item.colorUp,
                    cardColorDown = item.colorDown,
                    month = item.month.number,
                    year = item.year.number,
                    index = index,
                    isDefaultVisible = item.isDefault,
                    isValidateVisible = item.isActiveToken,
                    scale = scale,
                    bankIcon = item.icon,
                    onCardSelected = {
                        onCardSelected(newList[index])
                    }
                )
            }
        }

    }


}

private fun List<Card>.defaultCardMoveToFirstPosition(): List<Card> {
    val defaultCard = this.find() { it.isDefault }
    defaultCard?.let { dfCard ->
        val cardsWithoutDefaultItem = this.filterNot { it.isDefault }.toMutableList()
        cardsWithoutDefaultItem.add(0, dfCard)
        return cardsWithoutDefaultItem
    }
    return this
}

@Preview
@Composable
@ThemePreviews
fun PreviewContent() {

    AppTheme {
        AppBackground(modifier = Modifier) {}
    }
    val list = listOf(
        Card(
            ownerName = "محمد حسینی",
            number = "6037997175607630",
            token = "asfdsdf",
            cvv2 = CVV2(number = "456"),
            month = Month(number = "09"),
            year = Year("1409"),
            bankName = com.jabozaroid.abopay.core.common.R.string.melli_bank,
            colorUp = com.jabozaroid.abopay.core.common.R.color.melli_up,
            colorDown = com.jabozaroid.abopay.core.common.R.color.melli_Down,
            icon = R.drawable.ic_abo_pay
        ), Card(
            ownerName = "ابوذر رقیب دوست",
            number = "5892101178084188",
            token = "asfdsdf",
            cvv2 = CVV2(number = "14587"),
            month = Month(number = "02"),
            year = Year("1406"),
            bankName = com.jabozaroid.abopay.core.common.R.string.melli_bank,
            colorUp = com.jabozaroid.abopay.core.common.R.color.melli_up,
            colorDown = com.jabozaroid.abopay.core.common.R.color.melli_Down,
            icon = R.drawable.ic_abo_pay

        ), Card(
            ownerName = "ساناز رمضان",
            number = "6037998645432",
            token = "asfdsdf",
            cvv2 = CVV2(number = "6598"),
            month = Month(number = "11"),
            year = Year("1490"),
            bankName = com.jabozaroid.abopay.core.common.R.string.melli_bank,
            colorUp = com.jabozaroid.abopay.core.common.R.color.melli_up,
            colorDown = com.jabozaroid.abopay.core.common.R.color.melli_Down,
            icon = R.drawable.ic_abo_pay

        ), Card(
            ownerName = "عطیه فریدونی",
            number = "6037997175607630",
            token = "asfdsdf",
            cvv2 = CVV2(number = "963"),
            month = Month(number = "10"),
            year = Year("1403"),
            bankName = com.jabozaroid.abopay.core.common.R.string.melli_bank,
            colorUp = com.jabozaroid.abopay.core.common.R.color.melli_up,
            colorDown = com.jabozaroid.abopay.core.common.R.color.melli_Down,
            icon = R.drawable.ic_abo_pay

        )
    )
    CardList(modifier = Modifier, list = list, {})
}
