package com.jabozaroid.abopay.feature.balance.viewmodel


import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.OtpModel
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.CardMediaPan
import com.jabozaroid.abopay.core.domain.model.balance.CardMediaToken
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithTokenParam
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.balance.GetBalanceVatUsecase
import com.jabozaroid.abopay.core.domain.usecase.balance.GetBalanceWithTokenUsecase
import com.jabozaroid.abopay.core.domain.usecase.balance.GetCardBalanceWithPanUsecase
import com.jabozaroid.abopay.core.domain.usecase.balance.GetOtpWithPanUsecase
import com.jabozaroid.abopay.core.domain.usecase.balance.GetOtpWithTokenUsecase
import com.jabozaroid.abopay.core.domain.usecase.balance.GetSourceCardsUsecase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.balance.model.BalanceAction
import com.jabozaroid.abopay.feature.balance.model.BalanceEvent
import com.jabozaroid.abopay.feature.balance.model.BalanceUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val getOtpWithPanUsecase: GetOtpWithPanUsecase,
    private val getOtpWithTokenUsecase: GetOtpWithTokenUsecase,
    private val getBalanceVatUsecase: GetBalanceVatUsecase,
    private val getCardBalanceWithPanUsecase: GetCardBalanceWithPanUsecase,
    private val getCardBalanceWithTokenUsecase: GetBalanceWithTokenUsecase,
    private val getSourceCardsUsecase: GetSourceCardsUsecase,
) :
    BaseViewModel<BalanceUiModel, BalanceAction, BalanceEvent>(initialState = BalanceUiModel()) {

    private lateinit var timer: CountDownTimer
    private lateinit var harimOtpResult: AboPayResult<HarimOtpResult?>
    private lateinit var result: AboPayResult<BalanceResult?>

    companion object {
        const val TAG = "BalanceViewModel"
    }

    init {
        setupTimer(300000)
        getBalanceVat()
        getSourceCards()
    }

    override val onRefresh: () -> Unit
        get() = {}

    //region CardNumber
    private fun setCardNumber(cardNumber: String) {

        val cardValidationData = ValidationUtil.validationCardNumber(cardNumber)
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    card = it.selectedCard.card.copy(
                        number = cardNumber,
                        errorMessage = cardValidationData.errorMessage,
                        icon = cardValidationData.cardIconColor,
                    ),
                )
            )
        }
    }
    //endregion

    //region searchCardBottomSheet
    private fun showCardBottomSheet() {
        updateState {
            it.copy(
                cardBottomSheet = it.cardBottomSheet.copy(
                    isVisible = true
                )
            )
        }
    }

    private fun hideCardBottomSheet() {
        updateState {
            it.copy(
                cardBottomSheet = it.cardBottomSheet.copy(
                    isVisible = false
                )
            )
        }
    }
    //endregion

    //region Otp
    private fun setOtp(otpPassword: String) {
        var errorMessage: Int? = null
        if (otpPassword.isBlank())
            errorMessage = com.jabozaroid.abopay.core.common.R.string.needed_field
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    otpModel = it.selectedCard.otpModel.copy(
                        otpCode = otpPassword,
                        errorMessage = errorMessage
                    )
                ),
            )
        }
    }

    private fun getBalanceOtp(card: Card) {

//        if (!validateCardForOtp(card)) return
        startTimer()
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                updateState {
                    it.copy(loading = false, hasError = false)
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
            if (card.isActiveToken == true) {
                val param = HarimOtpWithTokenParam(
                    amount = 0,
                    cardMedia = CardMediaToken(token = card.token),
                    requestType = 6,//This Type for get Balance otp from harim and is always 6
                )
                harimOtpResult = getOtpWithTokenUsecase.execute(param)
            } else {
                val param = HarimOtpWithPanParam(
                    amount = 0,
                    cardMediaPan = CardMediaPan(pan = card.number),
                    requestType = 6,//This Type for get Balance otp from harim and is always 6
                )
                harimOtpResult = getOtpWithPanUsecase.execute(param)
            }

            harimOtpResult.onAboPayException { throwable ->
                Log.d(TAG, "GetOtpWithPan onError: ${throwable.message}")
                updateState {
                    it.copy(loading = false)
                }
                sendEvent(IEvent.ShowSnackMessage("${throwable.message}"))
            }
            harimOtpResult.onAboPayApiError { apiError ->
                Log.d(
                    TAG,
                    "GetOtpWithPan onApiErrorHandler: code: ${apiError.error.code} message : ${apiError.error.message}"
                )
                updateState {
                    it.copy(loading = false)
                }
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
            }
            harimOtpResult.onAboPaySuccess {
                sendEvent(IEvent.ShowToast(com.jabozaroid.abopay.core.common.R.string.snack_sent_dynamic_password))
            }

        }
    }


    //endregion

    //region OTP TIMER
    private fun setupTimer(timeMillis: Long) {
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateState {
                    it.copy(
                        selectedCard = it.selectedCard.copy(
                            otpModel = it.selectedCard.otpModel.copy(
                                timeLeft = FormatUtil.toDualTimeFormat(millisUntilFinished)
                            )
                        ),
                    )
                }
            }

            override fun onFinish() {
                updateState {
                    it.copy(
                        selectedCard = it.selectedCard.copy(
                            otpModel = it.selectedCard.otpModel.copy(
                                timeLeft = "درخواست مجدد", enableOtpRequest = true
                            )
                        ),
                    )
                }
            }
        }

    }

    private fun startTimer() {
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    otpModel = it.selectedCard.otpModel.copy(
                        enableOtpRequest = false,
                    )
                ),

                )
        }
        timer.start()
    }

    private fun stopTimer() {
        timer.cancel()
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    otpModel = it.selectedCard.otpModel.copy(
                        enableOtpRequest = true
                    )
                ),

                )
        }
    }
//endregion

    //region CVV2
    private fun setCVV2(cvv2: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCVV2(cvv2) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.needed_field
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    card = it.selectedCard.card.copy(
                        cvv2 = it.selectedCard.card.cvv2.copy(
                            number = cvv2,
                            errorMessage = errorMessage
                        )
                    )
                )
            )
        }

    }
    //endregion

    //region Month
    private fun setMonth(month: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardMonth(month) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.error
//
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    card = it.selectedCard.card.copy(
                        month = it.selectedCard.card.month.copy(
                            number = month,
                            errorMessage = errorMessage
                        )
                    )
                ),
            )
        }
    }
    //endregion

    //region Year
    private fun setYear(year: String) {

        var errorMessage: Int? = null
        if (ValidationUtil.validationCardYear(year) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    card = it.selectedCard.card.copy(
                        year = it.selectedCard.card.year.copy(
                            errorMessage = errorMessage,
                            number = year
                        )
                    )
                ),
            )
        }
    }
    //endregion

    private fun getBalanceVat() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                updateState {
                    it.copy(hasError = false)
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
            getBalanceVatUsecase.execute(Unit)

                .onAboPayException { throwable ->
                    Log.d(TAG, "onExceptionErrorHandlerGetVaton: ${throwable.message}")
                    updateState {
                        it.copy(hasError = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage("${throwable.message}"))
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "GetVat onApiErrorHandler: code: ${apiError.error.code} message : ${apiError.error.message}"
                    )
                    updateState {
                        it.copy(hasError = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                }
                .onAboPaySuccess { balanceVatResult ->
                    updateState {
                        it.copy(
                            vat = balanceVatResult?.vat
                        )
                    }
                }

        }
    }

    //region Balance
    private fun getInquiry(cardInformation: CardInformationUiModel) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
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
            }
        ) {
            updateState {
                it.copy(
                    loading = true
                )
            }
            if (cardInformation.card.isActiveToken == true) {
                result = getCardBalanceWithTokenUsecase.execute(
                    param = CardBalanceWithTokenParam(
                        token = cardInformation.card.token,
                        cvv2 = cardInformation.card.cvv2.number,
                        ExpireDate = cardInformation.card.year.number + cardInformation.card.month.number,
                        pin = cardInformation.otpModel.otpCode
                    )
                )
            } else {
                result = getCardBalanceWithPanUsecase.execute(
                    param = CardBalanceWithPanParam(
                        pan = cardInformation.card.number,
                        cvv2 = cardInformation.card.cvv2.number,
                        expireDate = cardInformation.card.year.number + cardInformation.card.month.number,
                        pin = cardInformation.otpModel.otpCode
                    )
                )
            }


            result.onAboPayException { throwable ->
                Log.d(TAG, "GetOtpWithPan onError: ${throwable.message}")
                updateState {
                    it.copy(loading = false)
                }
                sendEvent(IEvent.ShowSnackMessage("${throwable.message}"))
            }
            result.onAboPayApiError { apiError ->
                Log.d(
                    TAG,
                    "GetOtpWithPan onApiErrorHandler: code: ${apiError.error.code} message : ${apiError.error.message}"
                )
                updateState {
                    it.copy(loading = false)
                }
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
            }
            result.onAboPaySuccess { balanceResult ->
                updateState {
                    it.copy(
                        loading = false,
                        isShowReceipt = true,
                        receipt = it.receipt.copy(
                            availableAmountSeparated = balanceResult?.availableAmountSeparated,
                            amountSeparated = balanceResult?.amountSeparated,
                            availableAmount = balanceResult?.availableAmount,
                            amount = balanceResult?.amount,
                            maskedPan = balanceResult?.maskedPan,
                            primaryAccNo = balanceResult?.primaryAccNo,
                            issuerName = balanceResult?.issuerName,
                            transactionDate = balanceResult?.transactionDate,
                            trace = balanceResult?.trace,
                            rrn = balanceResult?.rrn,
                            point = balanceResult?.point
                        )
                    )
                }
            }
        }
    }
    //endregion

    private fun hideReceiptView() {
        resetReceipt()
    }

    private fun resetReceipt() {
        updateState {
            it.copy(
                isShowReceipt = false,
                receipt = it.receipt.copy(
                    availableAmountSeparated = null,
                    amountSeparated = null,
                    availableAmount = null,
                    amount = null,
                    maskedPan = null,
                    primaryAccNo = null,
                    issuerName = null,
                    transactionDate = null,
                    trace = null,
                    rrn = null,
                    point = null,
                )
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

    private fun getSourceCards() {
        viewModelScope.launch {
            getSourceCardsUsecase.execute(Unit)
                .onAboPaySuccess { sourceCards ->
                    val cards: MutableList<Card> = mutableListOf()
                    sourceCards.forEach { sourceCard ->
                        cards.add(
                            Card(
                                ownerName = sourceCard?.ownerName,
                                number = sourceCard?.number,
                                token = sourceCard?.token ?: "",
                                isActiveToken = sourceCard?.isActiveToken ?: false,
                                month = Month(
                                    number = sourceCard?.month?.number,
                                    errorMessage = sourceCard?.month?.errorMessage
                                ),
                                year = Year(
                                    number = sourceCard?.year?.number,
                                    errorMessage = sourceCard?.year?.errorMessage
                                ),
                                icon = sourceCard?.icon,
                                bankName = sourceCard?.bankName,
                                colorUp = sourceCard?.colorUp,
                                colorDown = sourceCard?.colorDown,
                                isDefault = sourceCard?.isDefault ?: false,
                                cardValidationLogo = sourceCard?.cardValidationLogo,
                                defaultCardLogo = sourceCard?.defaultCardLogo,
                            )
                        )
                    }
                    updateState {
                        it.copy(
                            sourceCardList = cards
                        )
                    }
                }
        }

    }

    private fun sourceCardSelected(card: Card) {
        if (currentState.selectedCard.card.number != card.number) {
            updateState {
                it.copy(
                    selectedCard = it.selectedCard.copy(
                        card = it.selectedCard.card.copy(
                            cvv2 = CVV2()
                        ),
                        otpModel = OtpModel()
                    )
                )
            }
        }
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    card = it.selectedCard.card.copy(
                        token = card.token,
                        number = card.number,
                        cardValidationLogo = card.cardValidationLogo,
                        defaultCardLogo = card.cardValidationLogo,
                        colorUp = card.colorUp,
                        colorDown = card.colorDown,
                        icon = card.icon,
                        month = card.month,
                        year = card.year, isDefault = card.isDefault,
                        bankName = card.bankName,
                        ownerName = card.ownerName,
                        isActiveToken = card.isActiveToken,
                        errorMessage = card.errorMessage
                    )
                )
            )
        }
    }


    private fun setAddNewSourceCard(cardNumber: String) {
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


    private fun setAddNewSourceCardMonth(cardMonth: String) {
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

    private fun setAddNewSourceCardYear(cardYear: String) {
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

    private fun setAddNewSourceCardOwnerName(ownerName: String) {
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
        if (card.isDefault) {
            setDefaultCardState()
        }

        val sourceCards = currentState.sourceCardList.toMutableList()
        if (sourceCards.filter { it.number == card.number }.isEmpty()) {
            val modifiedCard = if (card.ownerName.isNullOrBlank())
                card.copy(ownerName = "")
            else
                card
            sourceCards.add(0, modifiedCard)
            updateState {
                it.copy(
                    sourceCardList = sourceCards
                )
            }
        } else {
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.duplicate_error))
        }
    }


    private fun setAddNewSourceCardState(state: Boolean) {
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

    private fun setDefaultCardState() {
        val newList = currentState.sourceCardList.toMutableList().map {
            it.copy(isDefault = false)
        }
        updateState {
            it.copy(
                sourceCardList = newList
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

    override fun handleAction(action: BalanceAction) {
        when (action) {
            is BalanceAction.ShowCardBottomSheet -> showCardBottomSheet()
            is BalanceAction.HideCardBottomSheet -> hideCardBottomSheet()
            is BalanceAction.SetCardNumber -> setCardNumber(action.cardNumber)
            is BalanceAction.SetOtp -> setOtp(action.otp)
            is BalanceAction.SendOtpButtonClicked -> getBalanceOtp(action.card)
            is BalanceAction.SetCvv2 -> setCVV2(action.cvv2)
            is BalanceAction.SetMonth -> setMonth(action.month)
            is BalanceAction.SetYear -> setYear(action.year)
            is BalanceAction.NavigateUp -> navigateBack()
            is BalanceAction.GetBalanceInquiry -> {
                getInquiry(action.cardInformation)
                stopTimer()
            }

            is BalanceAction.HideBalanceReceipt -> hideReceiptView()
            is BalanceAction.OnHideAddCardBottomSheet -> userAddCartBottomSheetVisibility(false)
            is BalanceAction.OnShowAddCardBottomSheet -> userAddCartBottomSheetVisibility(true)
            is BalanceAction.OnSourceCardSelected -> sourceCardSelected(action.card)
            is BalanceAction.OnAddSourceCardNumberChange -> setAddNewSourceCard(action.cardNumber)
            is BalanceAction.OnAddSourceCardMonthChange -> setAddNewSourceCardMonth(action.cardMonth)
            is BalanceAction.OnAddSourceCardYearChange -> setAddNewSourceCardYear(action.cardYear)
            is BalanceAction.OnAddSourceCardOwnerNameChanged -> setAddNewSourceCardOwnerName(action.ownerName)
            is BalanceAction.OnAddNewSourceCard -> updateSourceCardList(action.card)
            is BalanceAction.OnDefaultAddSourceCardStateChanged -> setAddNewSourceCardState(action.state)
        }
    }

}