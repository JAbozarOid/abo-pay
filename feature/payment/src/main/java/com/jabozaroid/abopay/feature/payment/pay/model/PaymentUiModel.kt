package com.jabozaroid.abopay.feature.payment.pay.model

import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState

/**
 *  Created on 8/28/2024 
 **/
data class PaymentUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val userCards: List<SearchItemModel> = mutableListOf(),
    val cardInformation: CardInformationUiModel = CardInformationUiModel(),
    val isDateEnable: Boolean = true,
    val toolbarTitle: String = "",
    val cardBottomSheet: CardBottomSheet = CardBottomSheet(),
    val paymentMetaData: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
        visualItems = mutableListOf(),
        paymentItems = mutableListOf(),
        commonItems = com.jabozaroid.abopay.core.common.model.CommonItems()
    ),
) : IViewState {
}

data class CardBottomSheet(
    val isVisible: Boolean = false,
)
