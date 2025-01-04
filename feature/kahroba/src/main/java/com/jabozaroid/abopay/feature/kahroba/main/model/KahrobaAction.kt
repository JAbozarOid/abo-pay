package com.jabozaroid.abopay.feature.kahroba.main.model

import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface KahrobaAction : IAction {
    data object NavigateUp : KahrobaAction
    data object NavigatToNfc : KahrobaAction
    data object OnHideAddCardBottomSheet : KahrobaAction
    data class OnAddNewSourceCard(val card: Card) : KahrobaAction
    data class OnAddSourceCardNumberChange(val cardNumber: String) : KahrobaAction
    data class OnAddSourceCardMonthChange(val cardMonth: String) : KahrobaAction
    data class OnAddSourceCardYearChange(val cardYear: String) : KahrobaAction
    data class OnAddSourceCardCvv2Change(val cardCvv2: String) : KahrobaAction
    data class OnDefaultAddSourceCardStateChanged(val state: Boolean) : KahrobaAction
    data object OnShowAddCardBottomSheet : KahrobaAction
    data class OnSourceCardSelected(val card: Card) : KahrobaAction
    data class ContinueToPayment(val cardInformation: CardInformationUiModel) : KahrobaAction
    data object OnShowConfirmOtpBottomSheet : KahrobaAction
    data object OnHideConfirmOtpBottomSheet : KahrobaAction
    data object OnShowHelperBottomSheet : KahrobaAction
    data object OnHideHelperBottomSheet : KahrobaAction
    data class OnConfirmOtpButton(val otpCode: String) : KahrobaAction
    data class OnChangeOtp(val otpCode: String) : KahrobaAction
    data object OnOtpRequest : KahrobaAction
}