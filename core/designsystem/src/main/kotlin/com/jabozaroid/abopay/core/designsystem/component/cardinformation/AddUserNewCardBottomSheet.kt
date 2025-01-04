package com.jabozaroid.abopay.core.designsystem.component.cardinformation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.SwitchWithLabel
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardplaceholder.CardPlaceHolder
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.common.R

//region add user new card bottom sheet
@Composable
fun AddUserNewCardBottomSheet(
    cardInformationUiModel: CardInformationUiModel,
    bottomSheetTitle: String,
    onHideBottomSheet: () -> Unit = {},
    onBtnConfirmClicked: () -> Unit = {},
    confirmBtnText: String,
    onUserCardNumberChanged: (String) -> Unit = {},
    onCardMonthChanged: (String) -> Unit = {},
    onCardYearChanged: (String) -> Unit = {},
    onCardCvv2Changed: (String) -> Unit = {},
    onCardOwnerNameChanged: (String) -> Unit = {},
    cardNumber: MutableState<String> = mutableStateOf(""),
    onDefaultCardStateChange: (Boolean) -> Unit,
    scanCardVisibility: Boolean = true,
    switchTitle: Int? = null,
    cvv2Enable: Boolean = false,
    isDescriptionEnable: Boolean = true,
    isEnableConfirmButton: Boolean = cardInformationUiModel.card.enableAddUserCardButton(),
    isCardDefault: Boolean? = null,
) {
    BottomSheetComponent(
        title = bottomSheetTitle,
        content = {
            AddNewCardContent(
                cardInformationUiModel = cardInformationUiModel,
                onUserCardNumberChanged = onUserCardNumberChanged,
                onCardMonthChanged = onCardMonthChanged,
                onCardYearChanged = onCardYearChanged,
                onCardOwnerNameChanged = onCardOwnerNameChanged,
                cardNumber = cardNumber,
                onDefaultCardStateChange = onDefaultCardStateChange,
                scanCardVisibility = scanCardVisibility,
                switchTitle = switchTitle,
                cvv2Enable = cvv2Enable,
                isDescriptionEnable = isDescriptionEnable,
                onCardCvv2Changed = onCardCvv2Changed,
                isCardDefault = isCardDefault
            )
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = onBtnConfirmClicked,
        btn1Text = confirmBtnText,
        isBtn1Enabled = cardInformationUiModel.card.enableAddUserCardButton(),
        dualActionBtn = false,
        isScrollable = true
    )

}
//endregion

@Composable
fun AddNewCardContent(
    cardInformationUiModel: CardInformationUiModel,
    onUserCardNumberChanged: (String) -> Unit = {},
    onCardMonthChanged: (String) -> Unit = {},
    onCardYearChanged: (String) -> Unit = {},
    onCardCvv2Changed: (String) -> Unit = {},
    onCardOwnerNameChanged: (String) -> Unit = {},
    cardNumber: MutableState<String> = mutableStateOf(""),
    onDefaultCardStateChange: (Boolean) -> Unit,
    scanCardVisibility: Boolean = true,
    switchTitle: Int? = null,
    cvv2Enable: Boolean = false,
    isDescriptionEnable: Boolean = true,
    isCardDefault: Boolean? = null,

    ) {
    CardPlaceHolder(
        modifier = Modifier.padding(horizontal = Dimens.size_8),
        cardNumber = cardInformationUiModel.card.number,
        month = cardInformationUiModel.card.month.number,
        year = cardInformationUiModel.card.year.number,
        bankName = cardInformationUiModel.card.bankName,
        cardColorUp = cardInformationUiModel.card.colorUp,
        cardColorDown = cardInformationUiModel.card.colorDown,
        bankIcon = cardInformationUiModel.card.icon,
        ownerName = cardInformationUiModel.card.ownerName,
        scale = 1f,
        isSelected = true
    )
    ScanCard(
        modifier = Modifier.padding(horizontal = Dimens.size_16),
        scanCardVisibility = scanCardVisibility
    ) {

    }
    CardInformation(
        model = cardInformationUiModel,
        onChangeCardTextFiled = onUserCardNumberChanged,
        onChangeMonth = onCardMonthChanged,
        onChangeYear = onCardYearChanged,
        onChangeCvv2 = onCardCvv2Changed,
        isEnableCardNumberTextFiledTrailingIcon = false,
        isEnableOtpBox = false,
        isEnableCVV2 = cvv2Enable,
        cardNumber = cardNumber
    )
    if (isDescriptionEnable) {
        AppTextField(
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.size_16,
                    vertical = Dimens.size_4
                ),
            value = cardInformationUiModel.card.ownerName ?: "",
            onValueChange = onCardOwnerNameChanged,
            placeHolder = aboPayStringResource(id = R.string.entry_card_title),
            placeHolderAlignment = Alignment.CenterEnd,
            label = aboPayStringResource(id = R.string.card_name_optional)
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SwitchWithLabel(
            label = aboPayStringResource(
                switchTitle ?: R.string.default_card
            ),
            state = isCardDefault ?: cardInformationUiModel.card.isDefault,
            onStateChange = onDefaultCardStateChange,
            modifier = Modifier
                .padding(
                    vertical = Dimens.size_8,
                    horizontal = Dimens.size_16
                )
                .align(Alignment.End),
        )
    }
}

@Preview
@Composable
@ThemePreviews
fun Preview() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            AddNewCardContent(
                onCardMonthChanged = {},
                onCardYearChanged = {},
                onDefaultCardStateChange = {},
                cardInformationUiModel = CardInformationUiModel()
            )
        }
    }
}