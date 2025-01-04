package com.jabozaroid.abopay.feature.cardtocard.confirmation.model

import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState

/**
 *  Created on 10/21/2024
 **/
data class ConfirmationUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val cardPaymentBottomSheet: CardToCardPaymentBottomSheet = CardToCardPaymentBottomSheet(),
    val transferModel: C2CInquiryModel = C2CInquiryModel(),
) : IViewState


data class CardToCardPaymentBottomSheet(
    val cardInformationUiModel: CardInformationUiModel = CardInformationUiModel(),
    val isBottomSheetVisible: Boolean = false
)


data class C2CInquiryModel(
    val inquiryToken: String = "",
    val sourcePan: String = "",
    val targetPan: String = "",
    val month: String = "",
    val year: String = "",
    val amount: String = "",
    val targetPanOwnerName: String = "",
    val transactionID: String = ""
)
