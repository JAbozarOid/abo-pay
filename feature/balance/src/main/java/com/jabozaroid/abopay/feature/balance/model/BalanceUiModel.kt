package com.jabozaroid.abopay.feature.balance.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.ui.model.IViewState

data class BalanceUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val selectedCard: CardInformationUiModel = CardInformationUiModel(),
    val cardBottomSheet: CardBottomSheet = CardBottomSheet(),
    val vat: String? = "",
    val isShowReceipt: Boolean = false,
    val receipt: BalanceResult = BalanceResult(),
    val sourceCardList: List<Card> = listOf(),
    val addCardBottomSheetUiModel: AddCardBottomSheetUiModel = AddCardBottomSheetUiModel(),
    val scanCardVisibility: Boolean = true,
    ) : IViewState {
}


data class CardBottomSheet(
    val isVisible: Boolean = false,
)

data class AddCardBottomSheetUiModel(
    val cardInformationUiModel: CardInformationUiModel = CardInformationUiModel(),
    val metaData: MetaData = MetaData(),
    val isBottomSheetVisible: Boolean = false,
)

data class MetaData(
    val amount: Amount = Amount(),
    val description: Description = Description(),
    val saveDestinationCard: Boolean = true,
)

data class Amount(
    val value: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class Description(
    val value: String = "",
    val errorMessage: String = "",
)
