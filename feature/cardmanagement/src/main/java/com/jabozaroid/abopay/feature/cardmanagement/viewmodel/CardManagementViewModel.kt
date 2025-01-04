package com.jabozaroid.abopay.feature.cardmanagement.viewmodel

import android.util.Log
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.OtpModel
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditSourceCardParam
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.AddCardUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.AddDestinationCardUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.DeleteCardUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.DeleteDestinationCardUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.EditCardUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.EditDestinationCardUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.GetDestinationCardsUseCase
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.GetMyCardsUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.cardmanagement.mapper.mapToUiModel
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementAction
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementEvent
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementUiModel
import com.jabozaroid.abopay.feature.cardmanagement.model.MyCardOptionsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created on 24,November,2024
 */

@HiltViewModel
class CardManagementViewModel @Inject constructor(
    private val myCardsUseCase: GetMyCardsUseCase,
    private val deleteCardUseCase: DeleteCardUseCase,
    private val editCardUseCase: EditCardUseCase,
    private val addCardUseCase: AddCardUseCase,
    private val getDestinationCardsUseCase: GetDestinationCardsUseCase,
    private val deleteDestinationCardUseCase: DeleteDestinationCardUseCase,
    private val addDestinationCardUseCase: AddDestinationCardUseCase,
    private val editDestinationCardUseCase: EditDestinationCardUseCase,
) : BaseViewModel<CardManagementUiModel, CardManagementAction, CardManagementEvent>(
    initialState = CardManagementUiModel()
) {

    init {
        getSourceCards()
        getDestinationCards()
    }

    override fun handleAction(action: CardManagementAction) {
        when (action) {
            is CardManagementAction.NavigateUp -> {
                navigateBack()
            }

            is CardManagementAction.OnDestinationCardDeleteClicked -> deleteDestinationCard()

            is CardManagementAction.OnDestinationCardEditClicked -> editDestinationCard(action.item)
            is CardManagementAction.OnCopyCardClicked -> copyToClipboard(
                action.clipboardManager,
                action.cardNumber
            )

            is CardManagementAction.OnUserCardSelected -> updateUserSelectedCard(action.card)


            is CardManagementAction.OnShowAddCardBottomSheet -> userAddCartBottomSheetVisibility(
                show = true
            )

            is CardManagementAction.OnHideAddCardBottomSheet -> userAddCartBottomSheetVisibility(
                show = false
            )

            is CardManagementAction.OnUserCardAdded -> addSourceCard(action.card)
            is CardManagementAction.OnUserCardMonthChanged -> setUserCardMonth(action.cardMonth)
            is CardManagementAction.OnUserCardYearChanged -> setUserCardYear(action.cardYear)
            is CardManagementAction.OnUserCardNumberChanged -> setSourceCard(action.cardNumber)
            is CardManagementAction.OnUserCardOwnerNameChanged -> setSourceCardOwnerName(action.ownerName)
            is CardManagementAction.OnDefaultCardStateChanged -> setDefaultCardState(action.state)
            is CardManagementAction.OnHideDefaultCardBottomSheet -> {
                defaultCardBottomSheetVisibility(false)
            }

            is CardManagementAction.OnShowDefaultCardBottomSheet -> {
                defaultCardBottomSheetVisibility(true)
            }

            is CardManagementAction.OnDefaultCardSelectedBottomSheet -> {
                setDefaultCardState()
            }

            is CardManagementAction.OnHideDeleteCardBottomSheet -> {
                deleteCardBottomSheetVisibility(false)
            }

            is CardManagementAction.OnShowDeleteCardBottomSheet -> {
                deleteCardBottomSheetVisibility(true)
            }

            is CardManagementAction.OnDeleteCardSelectedBottomSheet -> {
//                deleteMyCardFromList()
//                deleteMyCard(currentState.selectedUserCard.token)
                deleteSourceCard()
            }

            is CardManagementAction.OnHideEditCardBottomSheet -> editCardBottomSheetVisibility(false)
            is CardManagementAction.OnShowEditCardBottomSheet -> {
                fillEditCardBottomSheetVisibility()
                editCardBottomSheetVisibility(true)
            }

            is CardManagementAction.OnEditCardSelectedBottomSheet -> editSourceCard()
            is CardManagementAction.OnHideEditDestinationCardBottomSheet -> editDestinationCartBottomSheetVisibility(
                false
            )

            is CardManagementAction.OnShowEditDestinationCardBottomSheet -> {
                setSelectedCardDestination(action.card)
                editDestinationCartBottomSheetVisibility(true)
            }

            is CardManagementAction.OnEditCardNameChanged -> {
                setUserEditCardName(action.name)
            }

            is CardManagementAction.OnEditCardMonthChanged -> {
                setUserEditCardMonth(action.month)
            }

            is CardManagementAction.OnEditCardYearChanged -> {
                setUserEditCardYear(action.year)
            }

            is CardManagementAction.OnShowAddDestinationCardBottomSheet -> {
                userAddDestinationCartBottomSheetVisibility(true)
            }

            is CardManagementAction.OnHideAddDestinationCardBottomSheet -> {
                userAddDestinationCartBottomSheetVisibility(false)
            }

            is CardManagementAction.OnAddDestinationCardClicked -> {
                addDestinationCard()
            }

            is CardManagementAction.OnAddDestinationCardNumberChanged -> {
                setDestinationCardNumberState(action.cardNumber)
            }

            is CardManagementAction.OnShowDeleteDestinationCardBottomSheet -> {
                setSelectedCardDestination(action.card)
                removeDestinationCardSheetVisibility(true)
            }

            is CardManagementAction.OnHideDeleteDestinationCardBottomSheet -> removeDestinationCardSheetVisibility(
                false
            )

        }
    }

    override val onRefresh = {

    }

    private fun getSourceCards() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            updateState {
                it.copy(loading = false)
            }

            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            updateState {
                it.copy(loading = true)
            }

            val result = myCardsUseCase.execute(Unit)

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(loading = false, aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess { myCardsResult ->
                updateState {
                    it.copy(
                        loading = false,
                        userCardList = myCardsResult?.myCards?.let { myCards ->
                            myCards.map { card ->
                                card.mapToUiModel()
                            }
                        } ?: emptyList()
                    )
                }
            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(loading = false, hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun deleteSourceCard() {
        //delete source card from local
        val updatedMyCardList = currentState.userCardList.toMutableList()
        updatedMyCardList.removeIf {
            it.number == currentState.selectedUserCard.number
        }
        updateState {
            it.copy(
                userCardList = updatedMyCardList.toList()
            )
        }

        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            val result = deleteCardUseCase.execute(
                DeleteSourceCardParam(
                    currentState.selectedUserCard.token
                )
            )

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(loading = false, aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess {

            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun editSourceCard() {
        val updatedMyCardList: MutableList<Card> = mutableListOf()
        currentState.userCardList.forEach {
            if (it.number == currentState.selectedUserCard.number)
                updatedMyCardList.add(
                    it.copy(
                        ownerName = currentState.editCardBottomSheetUiModel.card.ownerName,
                        month = currentState.editCardBottomSheetUiModel.card.month,
                        year = currentState.editCardBottomSheetUiModel.card.year,
                    )
                )
            else
                updatedMyCardList.add(it)
        }
        updateState {
            it.copy(
                userCardList = updatedMyCardList.toList()
            )
        }

        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            val result = editCardUseCase.execute(
                EditSourceCardParam(
                    //this line must be uncommented once expiredate will be fetched from getAllSourceCards api
//                    expireDate = "${currentState.selectedUserCard.year.number}${currentState.selectedUserCard.month.number}",
                    expireDate = "0505",
                    extraData = currentState.editCardBottomSheetUiModel.card.ownerName ?: "",
                    isDefault = currentState.selectedUserCard.isDefault,
                    token = currentState.selectedUserCard.token
                )
            )

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess {

            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun addSourceCard(card: Card) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            updateState {
                it.copy(loading = false)
            }

            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            updateState {
                it.copy(loading = true)
            }

            val result = addCardUseCase.execute(
                AddSourceCardParam(
                    expireDate = "${card.year.number}${card.month.number}",
                    extraData = card.ownerName ?: "",
//                    isDefault = card.isDefault,
                    primaryAccNo = card.number ?: ""
                )
            )

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(loading = false, aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess { response ->
                response?.let { res ->
                    res.success?.let { success ->
                        if (success) {
                            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.successful_add_card))
                            getSourceCards()
                        }
                    }

                }
                updateState {
                    it.copy(
                        loading = false
                    )
                }
            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(loading = false, hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun getDestinationCards() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            updateState {
                it.copy(loading = false)
            }

            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            updateState {
                it.copy(loading = true)
            }

            val result = getDestinationCardsUseCase.execute(Unit)

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(loading = false, aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess { destinationCardsResult ->
                updateState {
                    it.copy(
                        loading = false,
                        destinationCards = destinationCardsResult?.destinationCards?.let { myCards ->
                            myCards.map { card ->
                                card.mapToUiModel()
                            }
                        } ?: emptyList()
                    )
                }
            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(loading = false, hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun deleteDestinationCard() {
        val updatedDestinationCardList = currentState.destinationCards.toMutableList()
        updatedDestinationCardList.remove(currentState.deleteDestinationCardBottomSheetUiModel.card)
        updateState {
            it.copy(
                destinationCards = updatedDestinationCardList.toList()
            )
        }
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            val result = deleteDestinationCardUseCase.execute(
                DeleteDestinationCardParam(
                    currentState.deleteDestinationCardBottomSheetUiModel.card.id.toInt()
                )
            )

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess {

            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun editDestinationCard(card: SearchItemModel) {
        val updatedDestinationCardList: MutableList<SearchItemModel> = mutableListOf()
        currentState.destinationCards.forEach {
            if (it.id == card.id)
                updatedDestinationCardList.add(card)
            else
                updatedDestinationCardList.add(it)
        }
        updateState {
            it.copy(
                destinationCards = updatedDestinationCardList.toList()
            )
        }

        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            val result = editDestinationCardUseCase.execute(
                EditDestinationCardParam(
                    title = card.title,
                    pan = card.subTitle
                )
            )

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess {

            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }

    private fun addDestinationCard() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            updateState {
                it.copy(loading = false)
            }

            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {
            updateState {
                it.copy(loading = true)
            }

            val result = addDestinationCardUseCase.execute(
                AddDestinationCardParam(
                    title = "",
                    pan = currentState.addDestinationCardBottomSheetUiModel.card.number
                )
            )

            result.onAboPayException { throwable ->
                updateState {
                    it.copy(loading = false, aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess {
                updateState {
                    it.copy(
                        loading = false
                    )
                }
            }
            result.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(loading = false, hasError = true, aboPayApiError = apiError)
                }
            }
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

    private fun userAddDestinationCartBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                addDestinationCardBottomSheetUiModel = it.addDestinationCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
    }

    private fun fillEditCardBottomSheetVisibility() {
        updateState {
            it.copy(
                editCardBottomSheetUiModel = it.editCardBottomSheetUiModel.copy(
                    card = it.editCardBottomSheetUiModel.card.copy(
                        ownerName = it.selectedUserCard.ownerName,
                        month = it.selectedUserCard.month,
                        year = it.selectedUserCard.year,
                    )
                )
            )
        }
    }

    private fun editCardBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                editCardBottomSheetUiModel = it.editCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
    }

    private fun setSelectedCardDestination(card: SearchItemModel) {
        updateState {
            it.copy(
                editDestinationCardBottomSheetUiModel = it.editDestinationCardBottomSheetUiModel.copy(
                    card = card
                ),
                deleteDestinationCardBottomSheetUiModel = it.deleteDestinationCardBottomSheetUiModel.copy(
                    card = card
                )
            )
        }
    }

    private fun editDestinationCartBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                editDestinationCardBottomSheetUiModel = it.editDestinationCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
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

    private fun setDestinationCardNumberState(cardNumber: String) {
        val cardValidationData = ValidationUtil.validationCardNumber(cardNumber)
        updateState {
            it.copy(
                addDestinationCardBottomSheetUiModel = it.addDestinationCardBottomSheetUiModel.copy(
                    card = it.addDestinationCardBottomSheetUiModel.card.copy(
                        number = cardNumber,
                        errorMessage = cardValidationData.errorMessage
                    ),
                )
            )
        }
    }

    private fun copyToClipboard(clipboardManager: ClipboardManager, cardNumber: String) {
        clipboardManager.setText(AnnotatedString(cardNumber))
        sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.card_number_saved_to_clipboard))
    }

    private fun updateUserSelectedCard(card: Card) {
        val updatedCardsActionList: MutableList<MyCardOptionsModel> = mutableListOf()
        currentState.myCardsActionList.forEach { action ->
            if (
                (action.id == 1 && card.bankName != com.jabozaroid.abopay.core.common.R.string.melli_bank)
                ||
                (action.id == 3 && card.isDefault)
                ||
                (action.id == 4 && card.isActiveToken)
            )
                updatedCardsActionList.add(action.copy(isEnabled = false))
            else
                updatedCardsActionList.add(action.copy(isEnabled = true))
        }
        updateState {
            it.copy(
                selectedUserCard = card,
                myCardsActionList = updatedCardsActionList
            )
        }
    }

    private fun defaultCardBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                defaultCardBottomSheetUiModel = it.defaultCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
    }

    private fun setDefaultCardState() {
        val updatedMyCardList: MutableList<Card> = mutableListOf()
        currentState.userCardList.forEach {
            if (it.number == currentState.selectedUserCard.number) {
                updatedMyCardList.add(it.copy(isDefault = true))
            } else
                updatedMyCardList.add(it.copy(isDefault = false))
        }
        updateState {
            it.copy(
                userCardList = updatedMyCardList.toList()
            )
        }
    }

    private fun deleteCardBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                deleteCardBottomSheetUiModel = it.deleteCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
    }

    private fun setUserEditCardName(cardName: String) {
        updateState {
            it.copy(
                editCardBottomSheetUiModel = it.editCardBottomSheetUiModel.copy(
                    card = it.editCardBottomSheetUiModel.card.copy(
                        ownerName = cardName
                    )
                )
            )
        }
        Log.d(
            "TAG",
            "setUserEditCardName: ${currentState.editCardBottomSheetUiModel.card.ownerName}"
        )
    }

    private fun setUserEditCardMonth(cardMonth: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardMonth(cardMonth) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                editCardBottomSheetUiModel = it.editCardBottomSheetUiModel.copy(
                    card = it.editCardBottomSheetUiModel.card.copy(
                        month = it.editCardBottomSheetUiModel.card.month.copy(
                            number = cardMonth,
                            errorMessage = errorMessage
                        )
                    )
                )
            )
        }
    }

    private fun setUserEditCardYear(cardYear: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardYear(cardYear) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                editCardBottomSheetUiModel = it.editCardBottomSheetUiModel.copy(
                    card = it.editCardBottomSheetUiModel.card.copy(
                        year = it.editCardBottomSheetUiModel.card.year.copy(
                            number = cardYear,
                            errorMessage = errorMessage
                        )
                    )
                )
            )
        }
    }

    private fun removeDestinationCardSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                deleteDestinationCardBottomSheetUiModel = it.deleteDestinationCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
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
}