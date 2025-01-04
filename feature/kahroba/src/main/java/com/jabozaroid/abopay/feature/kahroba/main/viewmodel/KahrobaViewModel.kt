package com.jabozaroid.abopay.feature.kahroba.main.viewmodel

import android.os.CountDownTimer
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.OtpModel
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaAction
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaEvent
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KahrobaViewModel @Inject constructor() :
    BaseViewModel<KahrobaUiModel, KahrobaAction, KahrobaEvent>(initialState = KahrobaUiModel()) {


    private lateinit var timer: CountDownTimer

    init {
        setupTimer(300000)
    }

    override val onRefresh: () -> Unit
        get() = {}


    private fun userAddCartBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    isBottomSheetVisible = show
                )
            )
        }
        if (!show)
            removeAllAddSourceCardDataInAddBottomSheet()
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
                        errorMessage = card.errorMessage,
                        kahrobaIsActiveWIthOtp = card.kahrobaIsActiveWIthOtp
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

    private fun setAddNewSourceCardCVV2(cvv2: String) {
        var errorMessage: Int? = null
        if (ValidationUtil.validationCVV2(cvv2) == ValidationState.INVALID)
            errorMessage = com.jabozaroid.abopay.core.common.R.string.needed_field
        updateState {
            it.copy(
                addCardBottomSheetUiModel = it.addCardBottomSheetUiModel.copy(
                    cardInformationUiModel = it.addCardBottomSheetUiModel.cardInformationUiModel.copy(
                        card = it.addCardBottomSheetUiModel.cardInformationUiModel.card.copy(
                            cvv2 = it.selectedCard.card.cvv2.copy(
                                number = cvv2,
                                errorMessage = errorMessage
                            )
                        )
                    )
                )
            )
        }

    }

    private fun onConfirmOtpBottomSheet(otpCode: String) {
        //TODO:Send Otp To server if success update card kahroba isActive with token
        otpBottomSheetVisibility(false)
    }


    private fun otpBottomSheetVisibility(show: Boolean) {
        if (show)
            startTimer()
        else
            stopTimer()

        updateState {
            it.copy(
                confirmOtpBottomSheet = it.confirmOtpBottomSheet.copy(
                    isVisible = show
                )
            )
        }
    }

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

    private fun setOtp(otpCode: String) {
        updateState {
            it.copy(
                selectedCard = it.selectedCard.copy(
                    otpModel = it.selectedCard.otpModel.copy(
                        otpCode = otpCode
                    )
                )
            )
        }
    }

    private fun helperBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                helperBottomSheet = it.helperBottomSheet.copy(
                    isVisible = show,
                    items = helperData,
                    errorMessage = ""
                )
            )
        }
    }

    private fun requestOtp() {
        //TODO:Call API
        startTimer()
    }

    private fun navigateToNfcScreen() {
        navigateTo(NavigationCommand.ToScreen(ApplicationRoutes.nfcScreenRoute))
    }

    override fun handleAction(action: KahrobaAction) {
        when (action) {
            is KahrobaAction.ContinueToPayment -> {
                if (!currentState.selectedCard.card.kahrobaIsActiveWIthOtp)
                    otpBottomSheetVisibility(true)
                else {
                    navigateToNfcScreen()
                }
            }

            is KahrobaAction.OnAddNewSourceCard -> updateSourceCardList(action.card)
            is KahrobaAction.OnAddSourceCardMonthChange -> setAddNewSourceCardMonth(action.cardMonth)
            is KahrobaAction.OnAddSourceCardNumberChange -> setAddNewSourceCard(action.cardNumber)
            is KahrobaAction.OnAddSourceCardYearChange -> setAddNewSourceCardYear(action.cardYear)
            is KahrobaAction.OnDefaultAddSourceCardStateChanged -> setAddNewSourceCardState(action.state)
            is KahrobaAction.OnChangeOtp -> setOtp(action.otpCode)
            is KahrobaAction.OnSourceCardSelected -> sourceCardSelected(action.card)
            is KahrobaAction.OnAddSourceCardCvv2Change -> setAddNewSourceCardCVV2(action.cardCvv2)
            is KahrobaAction.OnConfirmOtpButton -> onConfirmOtpBottomSheet(action.otpCode)
            KahrobaAction.OnHideAddCardBottomSheet -> userAddCartBottomSheetVisibility(false)
            KahrobaAction.OnShowAddCardBottomSheet -> userAddCartBottomSheetVisibility(true)
            KahrobaAction.OnHideConfirmOtpBottomSheet -> otpBottomSheetVisibility(false)
            KahrobaAction.OnShowConfirmOtpBottomSheet -> otpBottomSheetVisibility(true)
            KahrobaAction.OnHideHelperBottomSheet -> helperBottomSheetVisibility(false)
            KahrobaAction.OnShowHelperBottomSheet -> helperBottomSheetVisibility(true)
            KahrobaAction.NavigateUp -> navigateBack()
            KahrobaAction.OnOtpRequest -> requestOtp()
            KahrobaAction.NavigatToNfc -> navigateToNfcScreen()
        }
    }

}
