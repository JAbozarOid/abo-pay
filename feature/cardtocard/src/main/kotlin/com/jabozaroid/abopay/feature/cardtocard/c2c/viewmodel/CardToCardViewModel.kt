package com.jabozaroid.abopay.feature.cardtocard.c2c.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.OtpModel
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.c2c.C2CInquiryWithPanUseCase
import com.jabozaroid.abopay.core.domain.usecase.c2c.C2CInquiryWithTokenUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardAction
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardEvent
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel
import com.jabozaroid.abopay.feature.cardtocard.c2c.preview.cardList
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.C2CInquiryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  Created on 9/29/2024
 **/
@HiltViewModel
class CardToCardViewModel @Inject constructor(
    private val c2cInquiryTokenUseCase: C2CInquiryWithTokenUseCase,
    private val c2cInquiryPanUseCase: C2CInquiryWithPanUseCase,
    private val sharedPrefStorage: SharedPrefStorage,

    ) : BaseViewModel<CardToCardUiModel, CardToCardAction, CardToCardEvent>(
    initialState = CardToCardUiModel(),
) {


    init {
        getDestinationCardList()
    }


    override val onRefresh: () -> Unit
        get() = { }

    override fun handleAction(action: CardToCardAction) {
        when (action) {
            is CardToCardAction.SetAmount -> validateAmount(action.amount)
            is CardToCardAction.OnDescriptionValueChanged -> validateDescription(action.description)
            is CardToCardAction.ShowTargetCardBottomSheet -> showCardBottomSheet(action.show)
            is CardToCardAction.SetTargetCardNumber -> setTargetCardNumber(action.cardNumber)

            is CardToCardAction.OnSaveStateChanged -> changeSavedCardState(action.saveDestinationCard)
            is CardToCardAction.OnToolbarIconClicked -> navigateBack()
            is CardToCardAction.OnShowAddCardBottomSheet -> userAddCartBottomSheetVisibility(show = true)
            is CardToCardAction.OnHideAddCardBottomSheet -> userAddCartBottomSheetVisibility(show = false)
            is CardToCardAction.OnConfirmButtonClicked -> prepareInquiryC2C()
            is CardToCardAction.OnUserCardNumberChanged -> setSourceCard(action.cardNumber)

            is CardToCardAction.OnUserCardMonthChanged -> setUserCardMonth(action.cardMonth)
            is CardToCardAction.OnUserCardYearChanged -> setUserCardYear(action.cardYear)
            is CardToCardAction.OnUserCardBtnAddClicked -> userAddCartBottomSheetVisibility(show = false)
            is CardToCardAction.OnCardItemDelete -> deleteDestinationCardItem(action.cardItem)
            is CardToCardAction.OnSourceCardSelected -> updateSourceCard(action.card)
            is CardToCardAction.OnUserCardAdded -> updateSourceCardList(action.card)
            is CardToCardAction.OnUserCardOwnerNameChanged -> setSourceCardOwnerName(action.ownerName)
            is CardToCardAction.OnDefaultCardStateChanged -> setDefaultCardState(action.state)
            is CardToCardAction.OnClearCardClicked -> clearDestinationCard(action.cardNumber, action.cleared)
        }
    }

    private fun clearDestinationCard(cardNumber: String, cleared: (Boolean) -> Unit) {
        val cardCommonModel: com.jabozaroid.abopay.core.common.model.CardCommonModel = ValidationUtil.validationCardNumber("")
        if (cardNumber.length == 16 && cardCommonModel.errorMessage != null) {
            setTargetCardNumber("")
            cleared(true)
        }
    }

    private fun updateSourceCard(card: Card) {
        updateState {
            it.copy(
                sourceCard = card
            )
        }
    }


    //region Add New CARD
    private fun showCardBottomSheet(show: Boolean) {
        updateState {
            it.copy(
                showCardListBottomSheet = show
            )
        }
    }

    private fun setUserCardMonth(cardMonth: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardMonth(cardMonth) == ValidationState.INVALID) errorMessage =
            com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = it.addCardBottomSheetUiModel.cardInformationUiModel.card.copy(
                            month = it.addCardBottomSheetUiModel.cardInformationUiModel.card.month.copy(
                                number = cardMonth, errorMessage = errorMessage
                            )
                        )
                    )
                )
            )
        }
    }

    private fun setUserCardYear(cardYear: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardYear(cardYear) == ValidationState.INVALID) errorMessage =
            com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = it.addCardBottomSheetUiModel.cardInformationUiModel.card.copy(
                            year = it.addCardBottomSheetUiModel.cardInformationUiModel.card.year.copy(
                                number = cardYear, errorMessage = errorMessage
                            )
                        )
                    )
                )
            )
        }
    }

    private fun setSourceCard(cardNumber: String) {
        val cardCommonModel: com.jabozaroid.abopay.core.common.model.CardCommonModel = ValidationUtil.validationCardNumber(cardNumber)

        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = it.addCardBottomSheetUiModel.cardInformationUiModel.card.copy(
                            number = cardNumber,
                            icon = cardCommonModel.cardIconWhite,
                            errorMessage = cardCommonModel.errorMessage,
                            bankName = cardCommonModel.bankName,
                            colorUp = cardCommonModel.cardColorUp,
                            colorDown = cardCommonModel.cardColorDown
                        )
                    )
                ),
                scanCardVisibility = cardCommonModel.errorMessage != null
            )


        }
    }

    private fun setSourceCardOwnerName(ownerName: String) {
        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = it.addCardBottomSheetUiModel.cardInformationUiModel.card.copy(
                            ownerName = ownerName
                        )
                    )
                )
            )

        }
    }

    private fun updateSourceCardList(card: Card) {
        var userCards = currentState.userCardList.toMutableList()

        val modifiedCard = if (card.ownerName.isNullOrBlank())
            card.copy(ownerName = "")
        else
            card

        if (card.isDefault)
            userCards = currentState.userCardList.toMutableList().map {
                it.copy(isDefault = false)
            }.toMutableList()

        userCards.add(0, modifiedCard)

        updateState {
            it.copy(
                userCardList = userCards
            )
        }
    }

    private fun userAddCartBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
        removeAllAddSourceCardDataInAddBottomSheet()
    }
    //endregion

    //region Handle DESTINATION CARD
    private fun setTargetCardNumber(cardNumber: String) {

        val cardCommonModel: com.jabozaroid.abopay.core.common.model.CardCommonModel = ValidationUtil.validationCardNumber(cardNumber)
        updateState {
            it.copy(
                destinationCard = it.destinationCard.copy(
                    number = cardNumber,
                    icon = if (cardNumber.length == 16 && cardCommonModel.errorMessage != null) com.jabozaroid.abopay.core.designsystem.R.drawable.ic_close else cardCommonModel.cardIconColor,
                    errorMessage = cardCommonModel.errorMessage
                )
            )
        }

    }

    //todo get destination CardList  from server and remove this
    private fun getDestinationCardList(): List<Card> {
        updateState {
            it.copy(
                destinationCardList = mapCardToSearchItem(cardList)
            )
        }

        return cardList
    }

    private fun mapCardToSearchItem(destinationCardList: List<Card>): MutableList<SearchItemModel> {
        val list = mutableListOf<SearchItemModel>()
        destinationCardList.forEach {
            val searchItemModel = SearchItemModel(
                id = it.token,
                title = it.ownerName ?: "",
                subTitle = it.number!!,
                icon = it.icon
            )
            list.add(searchItemModel)
        }
        return list
    }

    private fun deleteDestinationCardItem(cardItem: SearchItemModel) {
        val destinationList = currentState.destinationCardList.toMutableList().filterNot {
            it.id == cardItem.id
        }

        updateState {
            it.copy(
                destinationCardList = destinationList
            )
        }

    }
    //endregion

    //region Validate MetaData
    private fun validateAmount(amount: String) {

        var errorMessage: Int? = null
        if (amount.isBlank()) errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_price

        updateState {
            it.copy(
                metaData = it.metaData.copy(
                    amount = it.metaData.amount.copy(
                        value = amount, errorMessage = errorMessage
                    )
                )
            )
        }
    }

    private fun validateDescription(description: String) {
        updateState {
            it.copy(
                metaData = it.metaData.copy(
                    description = it.metaData.description.copy(
                        value = description
                    )
                )
            )
        }
    }

    private fun changeSavedCardState(saveCard: Boolean) {
        updateState {
            it.copy(
                metaData = it.metaData.copy(
                    saveDestinationCard = saveCard
                )
            )
        }
    }

    private fun setDefaultCardState(state: Boolean) {
        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = it.addCardBottomSheetUiModel.cardInformationUiModel.card.copy(
                            isDefault = state
                        )
                    )
                )
            )
        }
    }
    //endregion

    //region Inquiry Card
    private fun prepareInquiryC2C() {
        val hasToken = currentState.sourceCard.token.isNotBlank()
        val param = C2CInquiryParam(
            amount = currentState.metaData.amount.value,
            destinationPan = currentState.destinationCard.number,
            if (hasToken) currentState.sourceCard.token else null,
            currentState.metaData.description.value,
            if (hasToken) null else currentState.sourceCard.number
        )
        if (hasToken) {
            inquiryWithToken(param)
        } else {
            inquiryWithPan(param)
        }
    }

    private fun inquiryWithPan(param: C2CInquiryParam) {
        updateState {
            it.copy(loading = true)
        }

        viewModelScope.launch {
            c2cInquiryPanUseCase.execute(param)
                .onAboPayException { throwable ->
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                    Log.d("onError****", throwable.text)

                }
                .onAboPayApiError { apiError ->
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} ${apiError.error.message}"))
                    Log.d(
                        "onApiErrorHandler****",
                        { apiError.error.code }.toString() + { apiError.error.message })
                }
                .onAboPaySuccess { data ->
                    if (data == null)
                        return@onAboPaySuccess

                    updateState {
                        it.copy(loading = false)
                    }
                    navigateToConfirmationScreen(data)
                }
        }
    }

    private fun inquiryWithToken(param: C2CInquiryParam) {
        updateState {
            it.copy(loading = true)
        }

        viewModelScope.launch {

            c2cInquiryTokenUseCase.execute(param)
                .onAboPayException { throwable ->
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                }
                .onAboPayApiError { apiError ->
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} ${apiError.error.message}"))
                }
                .onAboPaySuccess { data ->
                    if (data == null) return@onAboPaySuccess
                    updateState {
                        it.copy(loading = false)
                    }
                    navigateToConfirmationScreen(data)
                }
        }
    }

    private fun navigateToConfirmationScreen(data: C2CInquiryResult) {
        val month = currentState.sourceCard.month.number
        val year = currentState.sourceCard.year.number
        if (month.isNullOrBlank() || year.isNullOrBlank()) return
        val card = C2CInquiryModel(
            month = month,
            year = year,
            inquiryToken = data.cardHolderInquiry.inquiryToken,
            sourcePan = currentState.sourceCard.number!!,
            amount = currentState.metaData.amount.value,
            targetPan = currentState.destinationCard.number!!,
            targetPanOwnerName = data.cardHolderInquiry.fullName
        )
        saveTransferModel(card)

        navigateTo(
            NavigationCommand.ToScreen(
                ApplicationRoutes.c2cConfrimationScreenRoute
            )
        )
    }

    private fun saveTransferModel(card: C2CInquiryModel) {
        sharedPrefStorage.save(StorageKey.C2C_TRANSFER_MODEL, card)
    }


    private fun removeAllAddSourceCardDataInAddBottomSheet() {
        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = Card(),
                        otpModel = OtpModel()
                    )
                )
            )
        }
    }

//endregion


    //region section 1-creating encryption for Card to Card Param
    // create logic for getting c2c route
    private fun inquiryDestinationPan() {

    }
    //endregion


    //region section 3- Shapark api - when redirect to app from tsm
    private fun completeTsmFlow() {

    }
    //endregion


}
