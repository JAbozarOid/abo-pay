package com.jabozaroid.abopay.feature.cardtocard.c2c.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.OtpModel
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.feature.cardtocard.c2c.preview.cardList


/**
 *  Created on 9/29/2024 
 **/
data class CardToCardUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    val sourceCard: Card = Card("", ""),
    val userCardList: List<Card> = cardList,
    val destinationCard: Card = Card("", ""),
    val destinationCardList: List<SearchItemModel> = mutableListOf(),
    val metaData: MetaData = MetaData(),
    val showCardListBottomSheet: Boolean = false,
    val addCardBottomSheetUiModel: AddCardBottomSheetUiModel = AddCardBottomSheetUiModel(),
    val otpModel: OtpModel = OtpModel(),
    val shaparakBottomSheet: ShaparakBottomSheet = ShaparakBottomSheet(),
    val scanCardVisibility: Boolean = true,
) : IViewState {
    fun isEnableC2CButton(): Boolean {
        return !destinationCard.number.isNullOrBlank() && !destinationCard.number.isNullOrBlank() &&
                destinationCard.number?.length == 16 && metaData.amount.value.isNotBlank()
    }

}

data class AddCardBottomSheetUiModel(
    val cardInformationUiModel: CardInformationUiModel = CardInformationUiModel(),
    val metaData: MetaData = MetaData(),
    val isBottomSheetVisible: Boolean = false
)

data class ShaparakBottomSheet(
    val isBottomSheetVisible: Boolean = false
)


data class Amount(
    val value: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class Description(
    val value: String = "",
    val errorMessage: String = "",
)

data class MetaData(
    val amount: Amount = Amount(),
    val description: Description = Description(),
    val saveDestinationCard: Boolean = true
)


