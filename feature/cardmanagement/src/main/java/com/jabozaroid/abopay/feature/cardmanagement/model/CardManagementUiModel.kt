package com.jabozaroid.abopay.feature.cardmanagement.model

import androidx.annotation.StringRes
import cardsActionList
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import mockedUserCardList
import searchItems

/**
 * Created on 24,November,2024
 */

data class CardManagementUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    val destinationCards: List<SearchItemModel> = searchItems,
    val userCardList: List<Card> = mockedUserCardList,
    val selectedUserCard: Card = Card(),
    val myCardsActionList: List<MyCardOptionsModel> = cardsActionList,
    val addCardBottomSheetUiModel: AddCardBottomSheetUiModel = AddCardBottomSheetUiModel(),
    val scanCardVisibility: Boolean = true,
    val defaultCardBottomSheetUiModel: DefaultCardBottomSheetUiModel = DefaultCardBottomSheetUiModel(),
    val deleteCardBottomSheetUiModel: DeleteCardBottomSheetUiModel = DeleteCardBottomSheetUiModel(),
    val editCardBottomSheetUiModel: EditCardBottomSheetUiModel = EditCardBottomSheetUiModel(),
    val editDestinationCardBottomSheetUiModel: EditDestinationCardBottomSheetUiModel = EditDestinationCardBottomSheetUiModel(
        searchItems[0]
    ),
    val deleteDestinationCardBottomSheetUiModel: DeleteDestinationCardBottomSheetUiModel = DeleteDestinationCardBottomSheetUiModel(
        searchItems[0]
    ),
    val addDestinationCardBottomSheetUiModel: AddDestinationCardBottomSheetUiModel = AddDestinationCardBottomSheetUiModel(),
) : IViewState


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

data class DefaultCardBottomSheetUiModel(
    val isBottomSheetVisible: Boolean = false,
)

data class DeleteCardBottomSheetUiModel(
    val isBottomSheetVisible: Boolean = false,
)

data class DeleteDestinationCardBottomSheetUiModel(
    val card: SearchItemModel,
    val isBottomSheetVisible: Boolean = false,
)

data class EditCardBottomSheetUiModel(
    val card: Card = Card(),
    val isBottomSheetVisible: Boolean = false,
)

data class EditDestinationCardBottomSheetUiModel(
    val card: SearchItemModel,
    val isBottomSheetVisible: Boolean = false,
)

data class AddDestinationCardBottomSheetUiModel(
    val card: DestinationCard = DestinationCard(),
    val isBottomSheetVisible: Boolean = false,
)

data class DestinationCard(
    val number: String = "",
    @StringRes val errorMessage: Int? = null,
)
