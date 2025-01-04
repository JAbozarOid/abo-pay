package com.jabozaroid.abopay.feature.payment.reciept.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.feature.payment.R

/**
 *  Created on 8/28/2024 
 **/
data class ReceiptUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    val otpModel: OtpModel = OtpModel(),
    val receiptModel: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
        visualItems = mutableListOf(),
        paymentItems = mutableListOf(),
        commonItems = com.jabozaroid.abopay.core.common.model.CommonItems(
            iconTitle = "",
            iconUrl = "",
            toolbarTitle = "",
            icon = R.drawable.ic_mokhaberat,
        )
    ),
) : IViewState

data class OtpModel(
    var otpCode: String = "",
    var enableOtpRequest: Boolean = true,
    var timeLeft: String = "درخواست رمز پویا",
)


