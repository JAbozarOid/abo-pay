package com.jabozaroid.abopay.feature.cardtocard.confirmation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.c2c.C2CTransferUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.C2CInquiryModel
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.ConfirmationAction
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.ConfirmationEvent
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.ConfirmationUiModel
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.C2CTransfer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  Created on 10/21/2024
 **/
@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val c2CTransferUseCase: C2CTransferUseCase,
    private val sharedPrefStorage: SharedPrefStorage
) :
    BaseViewModel<ConfirmationUiModel, ConfirmationAction, ConfirmationEvent>(
        initialState = ConfirmationUiModel()
    ) {

    private lateinit var timer: CountDownTimer

    init {
        setupTimer(300000)
    }

    override val onRefresh: () -> Unit
        get() = {

        }

    override fun handleAction(action: ConfirmationAction) {
        when (action) {
            is ConfirmationAction.OnCancelButtonClicked -> navigateBack()
            is ConfirmationAction.OnConfirmButtonClicked -> showPaymentBottomSheet()
            is ConfirmationAction.OnToolbarBackClicked -> navigateBack()
            is ConfirmationAction.SendOtpButtonClicked -> getOtp()
            is ConfirmationAction.SetCvv2 -> setUserCardCVV2(action.cvv2)
            is ConfirmationAction.SetMonth -> setUserCardMonth(action.month)
            is ConfirmationAction.SetOtp -> setUserCardOtp(action.otp)
            is ConfirmationAction.SetYear -> setUserCardYear(action.year)
            is ConfirmationAction.OnPaymentButtonClicked -> prepareTransfer()
            is ConfirmationAction.OnHidePaymentBottomSheet -> hidePaymentBottomSheet()
            is ConfirmationAction.OnUpdateCardInfo -> updateTransferData()
        }
    }


    private fun getTransferData(): C2CInquiryModel? {
        return sharedPrefStorage.get(StorageKey.C2C_TRANSFER_MODEL, C2CInquiryModel::class.java)
    }

    private fun updateTransferData() {
        val card = getTransferData() ?: return
        updateState {
            it.copy(
                transferModel = card
            )
        }
        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        card = it.cardPaymentBottomSheet.cardInformationUiModel.card.copy(
                            year = it.cardPaymentBottomSheet.cardInformationUiModel.card.year.copy(
                                number = card.year
                            )
                        )
                    )
                )
            )
        }
        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        card = it.cardPaymentBottomSheet.cardInformationUiModel.card.copy(
                            month = it.cardPaymentBottomSheet.cardInformationUiModel.card.month.copy(
                                number = card.month
                            )
                        )
                    )
                )
            )
        }
    }

    private fun hidePaymentBottomSheet() {
        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    isBottomSheetVisible = false
                )
            )
        }
    }


    private fun showPaymentBottomSheet() {
        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    isBottomSheetVisible = true
                )
            )
        }
    }


    private fun setUserCardMonth(cardMonth: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardMonth(cardMonth) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        card = it.cardPaymentBottomSheet.cardInformationUiModel.card.copy(
                            month = it.cardPaymentBottomSheet.cardInformationUiModel.card.month.copy(
                                number = cardMonth,
                                errorMessage = errorMessage
                            )
                        )
                    )
                )
            )
        }
    }

    private fun setUserCardYear(cardYear: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCardYear(cardYear) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.error

        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        card = it.cardPaymentBottomSheet.cardInformationUiModel.card.copy(
                            year = it.cardPaymentBottomSheet.cardInformationUiModel.card.year.copy(
                                number = cardYear,
                                errorMessage = errorMessage
                            )
                        )
                    )
                )
            )
        }
    }

    //region Otp
    private fun setUserCardOtp(otpPassword: String) {
        var errorMessage: Int? = null
        if (otpPassword.isBlank())
            errorMessage = com.jabozaroid.abopay.core.common.R.string.needed_field
        updateState {
            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        otpModel = it.cardPaymentBottomSheet.cardInformationUiModel.otpModel.copy(
                            otpCode = otpPassword,
                            errorMessage = errorMessage
                        ),
                    )
                ),
            )
        }
    }

    private fun getOtp() {

        sendEvent(IEvent.ShowToast(com.jabozaroid.abopay.core.common.R.string.snack_sent_dynamic_password))
        startTimer()
        viewModelScope.launch {}
    }
    //endregion

    //region OTP TIMER
    private fun setupTimer(timeMillis: Long) {
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateState {
                    it.copy(
                        cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                            cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                                otpModel = it.cardPaymentBottomSheet.cardInformationUiModel.otpModel.copy(
                                    timeLeft = FormatUtil.toDualTimeFormat(millisUntilFinished)
                                ),
                            )
                        ),
                    )
                }
            }

            override fun onFinish() {
                updateState {
                    it.copy(
                        cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                            cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                                otpModel = it.cardPaymentBottomSheet.cardInformationUiModel.otpModel.copy(
                                    timeLeft = "درخواست مجدد", enableOtpRequest = true
                                ),
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
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        otpModel = it.cardPaymentBottomSheet.cardInformationUiModel.otpModel.copy(
                            enableOtpRequest = false,
                        ),
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
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        otpModel = it.cardPaymentBottomSheet.cardInformationUiModel.otpModel.copy(
                            enableOtpRequest = true
                        ),
                    )
                ),
            )
        }
    }
//endregion

    //region CVV2
    private fun setUserCardCVV2(cvv2: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCVV2(cvv2) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.needed_field
        updateState {

            it.copy(
                cardPaymentBottomSheet = it.cardPaymentBottomSheet.copy(
                    cardInformationUiModel = it.cardPaymentBottomSheet.cardInformationUiModel.copy(
                        card = it.cardPaymentBottomSheet.cardInformationUiModel.card.copy(
                            cvv2 = it.cardPaymentBottomSheet.cardInformationUiModel.card.cvv2.copy(
                                errorMessage = errorMessage,
                                number = cvv2
                            ),
                        ),
                    )
                ),
            )
        }
    }
    //endregion


    //region Transfer
    private fun prepareTransfer() {
        stopTimer()

        val year =
            currentState.cardPaymentBottomSheet.cardInformationUiModel.card.year.number?.substring(2)
        val expDate =
            year.plus(currentState.cardPaymentBottomSheet.cardInformationUiModel.card.month.number)

        val param = C2CTransferParam(

            pin = currentState.cardPaymentBottomSheet.cardInformationUiModel.otpModel.otpCode,
            cvv2 = currentState.cardPaymentBottomSheet.cardInformationUiModel.card.cvv2.number,
            expireDate = expDate,
            inquiryToken = currentState.transferModel.inquiryToken
        )

        doTransfer(param)
    }

    private fun doTransfer(param: C2CTransferParam) {
        updateState {
            it.copy(loading = true)
        }

        viewModelScope.launch {

            c2CTransferUseCase.execute(param)

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
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} + ${apiError.error.message}"))
                }


                .onAboPaySuccess { data ->
                if (data == null) {
                    sendEvent(IEvent.ShowToast(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                    return@onAboPaySuccess
                }
                handleResult(data)
            }
        }
    }

    private fun handleResult(data: C2CTransferResult) {
        updateState {
            it.copy(loading = false)
        }
        val mappedData = mapResult(data)
        if (mappedData.rrn.isBlank() || mappedData.transactionId.isBlank()) {
            sendEvent(IEvent.ShowToast(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            return
        }

        saveC2CTransferResult(mappedData)


    }

    private fun mapResult(data: C2CTransferResult): C2CTransfer {
        return C2CTransfer(
            inquiryToken = currentState.transferModel.inquiryToken,
            sourcePan = currentState.transferModel.sourcePan,
            amount = data.amount,
            destinationPan = currentState.transferModel.targetPan,
            destinationPanOwner = currentState.transferModel.targetPanOwnerName,
            rrn = data.rrn,
            trackingNumber = data.trackingNumber,
            stan = data.stan,
            status = data.status,
            transactionDate = data.transactionDate,
            additionalResponseData = data.additionalResponseData,
            securityFactor = data.securityFactor,
            transactionId = data.transactionId,
            registrationDate = data.registrationDate,
            statusText = data.statusText
        )
    }


    private fun saveC2CTransferResult(mappedData: C2CTransfer) {

        sharedPrefStorage.save(StorageKey.C2C_TRANSFER_RESULT, mappedData)

        navigateToReceiptScreen()

    }

    private fun navigateToReceiptScreen() {
        navigateTo(
            NavigationCommand.ToScreen(
                ApplicationRoutes.c2cReceiptScreenRoute
            )
        )
    }

    //endregion

}