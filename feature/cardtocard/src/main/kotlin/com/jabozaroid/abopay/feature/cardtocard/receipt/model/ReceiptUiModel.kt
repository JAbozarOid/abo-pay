package com.jabozaroid.abopay.feature.cardtocard.receipt.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState

/**
 *  Created on 10/21/2024
 **/
data class ReceiptUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val transferResultModel : C2CTransfer = C2CTransfer(),
    val receiptItemModel : ReceiptItemModel = ReceiptItemModel(metadataItems = listOf()),

    ) : IViewState

data class ReceiptItemModel(
    val amount: String = "",
    val dateTime :String = "",
    val metadataItems: List<com.jabozaroid.abopay.core.common.model.MetaDataModel>
)

data class C2CTransfer(
    val trackingNumber : String = "",
    val amount: String="",
    val destinationPan: String="",
    val sourcePan: String="",
    val rrn: String="",
    val statusText: String="",
    val transactionDate: String="",
    val transactionId: String="",
    val registrationDate: Long = -1,
    val status: Int=-1,
    val stan: String="",
    val securityFactor :String="",
    val additionalResponseData : String="",
    val inquiryToken : String="",
    val destinationPanOwner: String=""
)



