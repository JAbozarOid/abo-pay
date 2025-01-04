package com.jabozaroid.abopay.feature.cardmanagement.view.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.InputVisualTransformation
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.feature.cardmanagement.model.AddDestinationCardBottomSheetUiModel

/**
 * Created on 02,December,2024
 */

@Composable
fun AddDestinationCardBottomSheet(
    addDestinationCardBottomSheetUiModel: AddDestinationCardBottomSheetUiModel,
    onCardNumberChanged: (String) -> Unit = {},
    onHideBottomSheet: () -> Unit = {},
    onBtnConfirmClicked: () -> Unit = {},
) {
    BottomSheetComponent(
        title = aboPayStringResource(id = R.string.card_number),
        content = {
            AddDestinationCardContent(
                addDestinationCardBottomSheetUiModel = addDestinationCardBottomSheetUiModel,
                onCardNumberChanged = onCardNumberChanged,
            )
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = onBtnConfirmClicked,
        onBtn2Click = onHideBottomSheet
    )
}

@Composable
fun AddDestinationCardContent(
    addDestinationCardBottomSheetUiModel: AddDestinationCardBottomSheetUiModel,
    onCardNumberChanged: (String) -> Unit = {},
    cardMask: String = "0000  0000  0000  0000",
    maskNumber: Char = '0',
) {
    AppTextField(
        textDirection = TextDirection.Ltr,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.size_16),
        value = addDestinationCardBottomSheetUiModel.card.number,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

        onValueChange = {
            if (it.all(Char::isDigit)) {
                onCardNumberChanged(it.take(cardMask.count { char ->
                    char == maskNumber
                }))
            }
        },
        visualTransformation = InputVisualTransformation(cardMask, maskNumber),
        label = stringResource(id = R.string.card_number),
        isError = !addDestinationCardBottomSheetUiModel.card.errorMessage.aboPayStringResource()
            .isNullOrBlank(),
        supportingText = addDestinationCardBottomSheetUiModel.card.errorMessage.aboPayStringResource(),
        placeHolder = "- - - -  - - - -  - - - -  - - - -",
    )
}

@Preview
@Composable
fun PreviewAddDestinationCardContent() {
    AppTheme {
        AppBackground(Modifier) {
            Column {
                AddDestinationCardContent(AddDestinationCardBottomSheetUiModel())
            }
        }
    }
}


