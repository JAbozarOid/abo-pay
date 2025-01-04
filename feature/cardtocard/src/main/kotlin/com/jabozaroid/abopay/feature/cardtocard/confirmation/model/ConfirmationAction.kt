package com.jabozaroid.abopay.feature.cardtocard.confirmation.model

import com.jabozaroid.abopay.core.ui.model.IAction

/**
 *  Created on 10/21/2024 
 **/
sealed interface ConfirmationAction : IAction {
    data object OnToolbarBackClicked : ConfirmationAction
    data object OnCancelButtonClicked : ConfirmationAction
    data object OnConfirmButtonClicked : ConfirmationAction
    data object OnPaymentButtonClicked : ConfirmationAction
    data object SendOtpButtonClicked: ConfirmationAction
    data class SetOtp(val otp: String) : ConfirmationAction
    data class SetCvv2(val cvv2: String) : ConfirmationAction
    data class SetMonth(val month: String) : ConfirmationAction
    data class SetYear(val year: String) : ConfirmationAction

    data object OnHidePaymentBottomSheet : ConfirmationAction
    data object OnUpdateCardInfo : ConfirmationAction



}