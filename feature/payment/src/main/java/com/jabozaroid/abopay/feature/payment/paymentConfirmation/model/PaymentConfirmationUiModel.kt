package com.jabozaroid.abopay.feature.payment.paymentConfirmation.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState


/**
 *  Created on 8/28/2024 
 **/
data class PaymentConfirmationUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    val amountWithVat: String = "",
    val paymentConfirmationModel: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
        paymentItems = mutableListOf(),
        visualItems = mutableListOf(),
        commonItems = com.jabozaroid.abopay.core.common.model.CommonItems()
    ),

    ) : IViewState


