package com.jabozaroid.abopay.feature.payment.reciept.preview

import com.jabozaroid.abopay.core.common.model.CommonItems
import com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel
import com.jabozaroid.abopay.feature.payment.R
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptUiModel

/**
 * Created on 20,November,2024
 */

val receiptUiModel = ReceiptUiModel(
    receiptModel = PaymentConfirmationModel(
        paymentItems = mutableListOf(),
        visualItems = mutableListOf(),
        commonItems = CommonItems(
            iconTitle = "قبض مخابرات",
            amount = "250000",
            iconUrl = "",
            toolbarTitle = "پرداخت قبض",
            icon = R.drawable.ic_mokhaberat,
        )
    )
)
