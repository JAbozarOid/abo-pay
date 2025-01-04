package com.jabozaroid.abopay.feature.payment.paymentConfirmation.model

import com.jabozaroid.abopay.core.ui.model.IAction


/**
 *  Created on 8/28/2024 
 **/
sealed interface PaymentConfirmationAction : IAction {

    data class ConfirmButtonClicked(val model : com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel) :
        PaymentConfirmationAction

    data object OnCancelButtonClicked : PaymentConfirmationAction
    data class OnUpdateConfirmationModel (val dataModel: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel):
        PaymentConfirmationAction
}