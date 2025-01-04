package com.jabozaroid.abopay.feature.balance.model

import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface BalanceAction : IAction {
    data object ShowCardBottomSheet : BalanceAction
    data object HideCardBottomSheet : BalanceAction
    data class SetCardNumber(val cardNumber: String) : BalanceAction
    data class SendOtpButtonClicked(val card: Card) : BalanceAction
    data class SetOtp(val otp: String) : BalanceAction
    data class SetCvv2(val cvv2: String) : BalanceAction
    data class SetMonth(val month: String) : BalanceAction
    data class SetYear(val year: String) : BalanceAction
    data object NavigateUp : BalanceAction
    data class GetBalanceInquiry(val cardInformation: CardInformationUiModel) : BalanceAction
    data object HideBalanceReceipt : BalanceAction
    data object OnShowAddCardBottomSheet : BalanceAction
    data object OnHideAddCardBottomSheet : BalanceAction
    data class OnSourceCardSelected(val card: Card) : BalanceAction
    data class OnAddSourceCardNumberChange(val cardNumber: String) : BalanceAction
    data class OnAddSourceCardMonthChange(val cardMonth: String) : BalanceAction
    data class OnAddSourceCardYearChange(val cardYear: String) : BalanceAction
    data class OnAddSourceCardOwnerNameChanged(val ownerName: String) : BalanceAction
    data class OnAddNewSourceCard(val card: Card) : BalanceAction
    data class OnDefaultAddSourceCardStateChanged(val state: Boolean) : BalanceAction


}