package com.jabozaroid.abopay.feature.cardmanagement.view.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.common.R
import searchItems

/**
 * Created on 02,December,2024
 */

@Composable
fun EditDestinationCardBottomSheet(
    card: SearchItemModel,
    onHideBottomSheet: () -> Unit = {},
    onBtnConfirmClicked: (SearchItemModel) -> Unit = {},
) {
    var cardNumber by remember { mutableStateOf(card.subTitle) }
    var cardName by remember { mutableStateOf(card.title) }
    BottomSheetComponent(
        title = aboPayStringResource(id = R.string.edit_destination_card),
        content = {
            EditDestinationCardContent(
                card = card,
                onCardNumberChanged = { cardNumber = it },
                onCardNameChanged = { cardName = it },
            )
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = {
            onBtnConfirmClicked(
                card.copy(
                    subTitle = cardNumber,
                    title = cardName,
                )
            )
        },
        onBtn2Click = onHideBottomSheet
    )
}

@Composable
fun EditDestinationCardContent(
    card: SearchItemModel,
    onCardNumberChanged: (String) -> Unit = {},
    onCardNameChanged: (String) -> Unit = {},
) {
    var cardNumber by remember { mutableStateOf(card.subTitle) }
    var cardName by remember { mutableStateOf(card.title) }

    AppTextField(
        modifier = Modifier.padding(horizontal = Dimens.size_24),
        value = cardNumber,
        onValueChange = {
            cardNumber = it
            onCardNumberChanged(it)
        },
        label = aboPayStringResource(id = R.string.card_number)
    )
    AppTextField(
        modifier = Modifier
            .padding(horizontal = Dimens.size_24)
            .padding(top = Dimens.size_12),
        textAlign = TextAlign.Start,
        value = cardName,
        onValueChange = {
            cardName = it
            onCardNameChanged(it)
        },
        label = aboPayStringResource(id = R.string.card_name_optional),
        isError = false,
        supportingText = aboPayStringResource(id = R.string.supporting_card_name)
    )
}

@Preview
@Composable
fun PreviewEditDestinationCardContent() {
    AppTheme {
        AppBackground(Modifier) {
            Column {
                EditDestinationCardContent(searchItems[0])
            }
        }
    }
}


