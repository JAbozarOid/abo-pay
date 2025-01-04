package com.jabozaroid.abopay.feature.payment.pay.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.domain.model.payment.KeyValue
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.cardmanagement.GetMyCardsUseCase
import com.jabozaroid.abopay.core.domain.usecase.payment.PaymentUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentAction
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentEvent
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentMedia
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentUiModel
import com.jabozaroid.abopay.feature.payment.pay.model.mapToSearchItem
import com.jabozaroid.abopay.feature.payment.pay.model.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentUseCase: PaymentUseCase,
    private val sharedPrefStorage: SharedPrefStorage,
    private val userCardUseCase: GetMyCardsUseCase,

    ) : BaseViewModel<PaymentUiModel, PaymentAction, PaymentEvent>(
    initialState = PaymentUiModel()
) {

    override val onRefresh: () -> Unit
        get() = { }

    private lateinit var timer: CountDownTimer

    init {
        setupTimer(300000)
        getUserCards()
    }


    override fun handleAction(action: PaymentAction) {
        action.let {
            when (it) {

                is PaymentAction.OnUpdatePaymentModel -> updatePaymentModel()
                is PaymentAction.SendOtpButtonClicked -> getPaymentOtp(it.card)
                is PaymentAction.PaymentButtonClicked -> doPayment(it.model)
                is PaymentAction.ReceiptButtonDoneClicked -> navigateToMainMenu()
                is PaymentAction.SetCardNumber -> setCardNumber(it.cardNumber, it.hasToken)
                is PaymentAction.SetCardToken -> setCardToken(it.cardToken)
                is PaymentAction.SetOtp -> setOtp(it.otp)
                is PaymentAction.SetCvv2 -> setCVV2(it.cvv2)
                is PaymentAction.SetMonth -> setMonth(it.month)
                is PaymentAction.SetYear -> setYear(it.year)
                is PaymentAction.HideCardBottomSheet -> hideCardBottomSheet()
                is PaymentAction.ShowCardBottomSheet -> showCardBottomSheet()
            }
        }
    }


    //region get User Card List
    private fun getUserCards() {
        viewModelScope.launch {
            currentState.cardInformation
            userCardUseCase.execute(Unit).onAboPaySuccess { result ->
                if (result == null) return@onAboPaySuccess
                updateState {
                    it.copy(
                        loading = false,
                        userCards = result.myCards.let { myCards ->
                            myCards.map { card ->
                                card.mapToUiModel().mapToSearchItem()
                            }
                        }
                    )
                }

            }.onAboPayException { throwable ->
                updateState {
                    it.copy(loading = false, aboPayException = throwable)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }.onAboPayApiError { apiError ->
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
                updateState {
                    it.copy(loading = false, hasError = true, aboPayApiError = apiError)
                }
            }
        }
    }
    //endregion


    //region Payment
    private fun getPaymentOtp(card: Card) {

        if (!validateCard(card)) return

        sendEvent(IEvent.ShowToast(com.jabozaroid.abopay.core.common.R.string.snack_sent_dynamic_password))

        startTimer()
        viewModelScope.launch {}
    }

    private fun doPayment(model: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel) {
        updateState {
            it.copy(
                loading = true
            )
        }

        stopTimer()

        if (mapToPaymentParam(model) == null) return
        viewModelScope.launch {
            paymentUseCase.execute(mapToPaymentParam(model)!!).onAboPaySuccess { paymentResult ->
                updateState {
                    it.copy(
                        loading = false
                    )
                }
                if (paymentResult == null) return@onAboPaySuccess
                savePaymentResult(paymentResult)

            }.onAboPayException {
                updateState {
                    it.copy(
                        loading = false,
                        aboPayException = AboPayExceptionMessage()
                    )
                }
            }
                .onAboPayApiError {
                    updateState {
                        it.copy(
                            loading = false,
                            aboPayApiError = AboPayServerError()
                        )
                    }
                }
        }

    }

    private fun mapToPaymentParam(model: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel): PaymentRequestParam? {
        val list = mutableListOf<KeyValue>()
        if (model.paymentItems.isEmpty()) return null

        if (currentState.cardInformation.card.token.isNotBlank()) {
            list.add(KeyValue(PaymentMedia.TOKEN.title, currentState.cardInformation.card.token))
        } else {
            list.add(
                KeyValue(
                    PaymentMedia.PAN.title,
                    currentState.cardInformation.card.number.toString()
                )
            )
        }
        list.add(
            KeyValue(
                PaymentMedia.CVV2.title,
                currentState.cardInformation.card.cvv2.number
            )
        )

        list.add(KeyValue(PaymentMedia.PIN.title, currentState.cardInformation.otpModel.otpCode))
        list.add(KeyValue(PaymentMedia.AMOUNT.title, model.commonItems.amount))
        list.add(
            KeyValue(
                PaymentMedia.EXPIRE_DATE.title,
                currentState.cardInformation.card.year.number.plus(currentState.cardInformation.card.month.number)
            )
        )
        for (item in model.paymentItems) {
            list.add(KeyValue(item.key, item.value))
        }
        return PaymentRequestParam(paymentType = model.commonItems.paymentType.id, list)

    }
    //endregion

    //region Save and Update Data
    private fun updatePaymentModel() {
        val model = getPaymentModel() ?: return
        updateState {
            it.copy(
                paymentMetaData = model
            )
        }
    }

    private fun getPaymentModel(): com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel? {
        return sharedPrefStorage.get(StorageKey.PAYMENT_MODEL, com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel::class.java)
    }

    private fun savePaymentResult(paymentResult: PaymentResult) {
        val receiptModel = getPaymentModel() ?: return

        receiptModel.visualItems = mapResultData(paymentResult.items)
        sharedPrefStorage.save(StorageKey.RECEIPT_MODEL, receiptModel)

        navigateToReceipt()
    }

    private fun mapResultData(list: List<KeyValue>): List<com.jabozaroid.abopay.core.common.model.MetaDataModel> {
        val items = mutableListOf<com.jabozaroid.abopay.core.common.model.MetaDataModel>()
        for (item in list) {
            items.add(com.jabozaroid.abopay.core.common.model.MetaDataModel(item.key, item.value))
        }
        return items

    }
    //endregion

    //region Payment Media
    private fun validateCard(card: Card): Boolean {

        var isValid = true
        var errorMessage: Int? = null

        if (card.token.isBlank()) {
            if (card.number.isNullOrEmpty()) {
                isValid = false
                errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_card_number
            }
        }

        updateState {
            it.copy(
                cardInformation = it.cardInformation.copy(
                    card = it.cardInformation.card.copy(
                        errorMessage = errorMessage,
                    )
                ),
            )
        }
        return isValid
    }

    private fun setCardNumber(cardNumber: String, hasToken: Boolean) {
        val cardValidation = ValidationUtil.validationCardNumber(cardNumber, hasToken)
        updateState {
            it.copy(
                cardInformation = it.cardInformation.copy(
                    card = it.cardInformation.card.copy(
                        number = cardNumber,
                        errorMessage = cardValidation.errorMessage,
                        icon = cardValidation.cardIconColor,
                    )
                ),

                )
        }
        handleExpireDate("")

    }

    private fun setCardToken(cardToken: String) {
        updateState {
            it.copy(
                cardInformation = it.cardInformation.copy(
                    card = it.cardInformation.card.copy(
                        token = cardToken,
                    )
                ),
            )
        }
        handleExpireDate(cardToken)
    }

    private fun handleExpireDate(token: String) {
        var hasExpDate = false
        if (token.isNotBlank()) {
            currentState.userCards.forEach {
                if (it.id == token) hasExpDate = it.hasValue
            }
            setYear("", hasExpDate)
            setMonth("", hasExpDate)
        }
        updateState { uiModel ->
            uiModel.copy(
                isDateEnable = !hasExpDate
            )
        }


    }

    private fun setOtp(otpPassword: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationOtp(otpPassword) == ValidationState.INVALID) errorMessage =
            com.jabozaroid.abopay.core.common.R.string.needed_field

        updateState {
            it.copy(
                cardInformation = it.cardInformation.copy(
                    otpModel = it.cardInformation.otpModel.copy(
                        otpCode = otpPassword,
                        errorMessage = errorMessage
                    )
                )
            )
        }
    }

    private fun setCVV2(cvv2: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCVV2(cvv2) == ValidationState.INVALID) errorMessage =
            com.jabozaroid.abopay.core.common.R.string.needed_field
        updateState {
            it.copy(
                cardInformation = it.cardInformation.copy(
                    card = it.cardInformation.card.copy(
                        cvv2 = it.cardInformation.card.cvv2.copy(
                            errorMessage = errorMessage, number = cvv2
                        )
                    )
                )
            )
        }
    }

    private fun setMonth(month: String, hasExpDate: Boolean = false) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardMonth(month) == ValidationState.INVALID && !hasExpDate) errorMessage =
            com.jabozaroid.abopay.core.common.R.string.error
        updateState {

            it.copy(
                cardInformation = it.cardInformation.copy(
                    card = it.cardInformation.card.copy(
                        month = it.cardInformation.card.month.copy(
                            number = month, errorMessage = errorMessage
                        )
                    )
                )
            )
        }
    }

    private fun setYear(year: String, hasExpDate: Boolean = false) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardYear(year) == ValidationState.INVALID && !hasExpDate) errorMessage =
            com.jabozaroid.abopay.core.common.R.string.error
        updateState {
            it.copy(
                cardInformation = it.cardInformation.copy(
                    card = it.cardInformation.card.copy(
                        year = it.cardInformation.card.year.copy(
                            number = year,
                            errorMessage = errorMessage
                        )
                    )
                ),
            )
        }
    }
    //endregion

    //region OTP TIMER
    private fun setupTimer(timeMillis: Long) {
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateState {
                    it.copy(
                        cardInformation = it.cardInformation.copy(
                            otpModel = it.cardInformation.otpModel.copy(
                                timeLeft = FormatUtil.toDualTimeFormat(millisUntilFinished)
                            )
                        ),
                    )
                }
            }

            override fun onFinish() {
                updateState {
                    it.copy(
                        cardInformation = it.cardInformation.copy(
                            otpModel = it.cardInformation.otpModel.copy(
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
                cardInformation = it.cardInformation.copy(
                    otpModel = it.cardInformation.otpModel.copy(
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
                cardInformation = it.cardInformation.copy(
                    otpModel = it.cardInformation.otpModel.copy(
                        enableOtpRequest = true
                    )
                ),
            )
        }
    }
//endregion

    //region USER CARD BottomSheet
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

    //region Navigation
    private fun navigateToReceipt() {
        navigateTo(
            command = NavigationCommand.ToScreen(
                ApplicationRoutes.paymentReceiptScreenRoute
            )
        )
    }

    private fun navigateToMainMenu() {
        navigateTo(
            command = NavigationCommand.ToScreen(
                ApplicationRoutes.homeScreenRoute, clearBackStack = true
            )
        )
    }
    //endregion


}


