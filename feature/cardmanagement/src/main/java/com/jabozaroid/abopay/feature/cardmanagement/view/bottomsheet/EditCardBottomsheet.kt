package com.jabozaroid.abopay.feature.cardmanagement.view.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.feature.cardmanagement.model.EditCardBottomSheetUiModel

/**
 * Created on 02,December,2024
 */

@Composable
fun EditCardBottomSheet(
    editCardBottomSheetUiModel: EditCardBottomSheetUiModel,
    onHideBottomSheet: () -> Unit = {},
    onCardNameChanged: (String) -> Unit = {},
    onMonthChanged: (String) -> Unit = {},
    onYearChanged: (String) -> Unit = {},
    onBtnConfirmClicked: () -> Unit = {},
) {
    BottomSheetComponent(
        title = aboPayStringResource(id = R.string.edit_card),
        content = {
            EditCardContent(
                editCardBottomSheetUiModel = editCardBottomSheetUiModel,
                onCardNameChanged = {
                    onCardNameChanged(it)
                },
                onCardYearChanged = {
                    onYearChanged(it)
                },
                onCardMonthChanged = {
                    onMonthChanged(it)
                }
            )
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = onBtnConfirmClicked,
        onBtn2Click = onHideBottomSheet
    )
}

@Composable
fun EditCardContent(
    editCardBottomSheetUiModel: EditCardBottomSheetUiModel,
    onCardNameChanged: (String) -> Unit = {},
    onCardMonthChanged: (String) -> Unit = {},
    onCardYearChanged: (String) -> Unit = {},
) {
    AppTextField(
        modifier = Modifier.padding(horizontal = Dimens.size_24),
        textAlign = TextAlign.Start,
        value = editCardBottomSheetUiModel.card.ownerName ?: "",
        onValueChange = {
            onCardNameChanged(it)
        },
        label = aboPayStringResource(id = R.string.card_name)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.size_12)
    ) {
        val maxYearLength = 2
        AppTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = Dimens.size_24),
                textDirection = TextDirection.Ltr,
            value = editCardBottomSheetUiModel.card.year.number ?: "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                if (it.all(Char::isDigit)) {
                    if (it.length <= maxYearLength) {
                        onCardYearChanged(it)
                    }
                }
            },
            label = aboPayStringResource(R.string.year),
            isError = !editCardBottomSheetUiModel.card.year.errorMessage.aboPayStringResource()
                .isNullOrEmpty(),
            supportingText = editCardBottomSheetUiModel.card.year.errorMessage.aboPayStringResource()
        )
        Spacer(modifier = Modifier.width(Dimens.size_12))
        val maxMonthLength = 2
        AppTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = Dimens.size_24),
                textDirection = TextDirection.Ltr,

            value = editCardBottomSheetUiModel.card.month.number ?: "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

            onValueChange = {
                if (it.all(Char::isDigit)) {
                    if (it.length <= maxMonthLength) {
                        onCardMonthChanged(it)
                    }
                }
            },
            label = aboPayStringResource(R.string.month),
            isError = !editCardBottomSheetUiModel.card.month.errorMessage.aboPayStringResource()
                .isNullOrEmpty(),
            supportingText = editCardBottomSheetUiModel.card.month.errorMessage.aboPayStringResource()
        )
    }
}


@Preview
@Composable
fun PreviewEditCardContent() {
    AppTheme {
        AppBackground(Modifier) {
            Column {
//                EditCardContent(Card())
            }
        }
    }
}


