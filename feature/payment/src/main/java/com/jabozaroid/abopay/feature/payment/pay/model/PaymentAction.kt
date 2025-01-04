package com.jabozaroid.abopay.feature.payment.pay.model

import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.ui.model.IAction

/**
 *  Created on 8/28/2024 
 **/
sealed interface PaymentAction : IAction {

    data object OnUpdatePaymentModel : PaymentAction
    data class PaymentButtonClicked(val model: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel) :
        PaymentAction
    data class SendOtpButtonClicked(val card: Card) : PaymentAction
    data object ReceiptButtonDoneClicked : PaymentAction
    data class SetCardNumber(val cardNumber: String, val hasToken: Boolean) : PaymentAction
    data class SetCardToken(val cardToken: String) : PaymentAction
    data class SetOtp(val otp: String) : PaymentAction
    data class SetCvv2(val cvv2: String) : PaymentAction
    data class SetMonth(val month: String) : PaymentAction
    data class SetYear(val year: String) : PaymentAction
    data object ShowCardBottomSheet : PaymentAction
    data object HideCardBottomSheet : PaymentAction

}