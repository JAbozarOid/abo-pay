package com.jabozaroid.abopay.feature.login.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.auth.GetLoginOtpUseCase
import com.jabozaroid.abopay.core.domain.usecase.auth.VerifyLoginOtpUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.login.model.LoginAction
import com.jabozaroid.abopay.feature.login.model.LoginAction.SendOtpButtonClicked
import com.jabozaroid.abopay.feature.login.model.LoginEvent
import com.jabozaroid.abopay.feature.login.model.LoginUiModel
import com.jabozaroid.abopay.feature.login.model.OtpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginOtpUseCase: GetLoginOtpUseCase,
    private val verifyOtpUseCase: VerifyLoginOtpUseCase,
) : BaseViewModel<LoginUiModel, LoginAction, LoginEvent>(
    initialState = LoginUiModel()
) {

    private lateinit var timer: CountDownTimer

    @Inject
    lateinit var sharedPrefStorage: SharedPrefStorage

    override val onRefresh: () -> Unit
        get() = { }


    init {
        setupTimer(300000)
    }


    override fun handleAction(action: LoginAction) {
        action.let {
            when (action) {

                is SendOtpButtonClicked -> {
                    if (!action.needValidation) {
                        startTimer()
                    }
                    getLoginOtp(action.mobile, action.nationalCode, action.needValidation)
                }

                is LoginAction.VerifyOtpButtonClicked -> verifyOtp(action.otpModel)
                is LoginAction.OnTimerStarted -> startTimer()
                is LoginAction.OnEditMobileClicked -> {
                    stopTimer()
                    navigateBack()
                }

                is LoginAction.OnMobileValueChanged -> {
                    validateMobile(mobile = action.mobile)
                }

                is LoginAction.OnNationalCodeChanged -> validateNationalCode(action.nationalCode)
                is LoginAction.OnOtpValueChanged -> validateOTP(action.otpCode)
            }
        }
    }


    private fun getLoginOtp(mobile: String, nationalCode: String, needValidation: Boolean) {

        if (needValidation && !validateMobile(mobile)) {
            return
        }

        updateState {
            it.copy(loading = true)
        }

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
        }
        ) {

            getLoginOtpUseCase.execute(
                LoginOtpParam(
                    mobile = mobile,
                    nationalCode = nationalCode
                )
            )
                .onAboPayException { it ->
                    sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                    Log.d("onError****", it.message!!)
                    updateState {
                        it.copy(loading = false)
                    }
                }

                .onAboPayApiError { apiError ->
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} ${apiError.error.message}"))
                    Log.d("onApiErrorHandler****", apiError.toString() + apiError)
                }

                .onAboPaySuccess { it ->
                    updateState {
                        it.copy(loading = false)
                    }
                    it?.result?.uniqueTraceNumber?.let { traceNumber ->
                        Log.d("traceNumber**********", traceNumber)
                        updateState {
                            it.copy(
                                otpModel = it.otpModel.copy(
                                    uniqueTraceNumber = traceNumber
                                )
                            )
                        }
                        if (needValidation) {
                            navigateToVerifyOtp(mobile, traceNumber, nationalCode)
                            stopTimer()
                        }
                    }
                }
        }
    }

    //region Validation
    private fun validateMobile(mobile: String): Boolean {
        var isValid = true
        var errorMessage: Int? = null

        if (mobile.isBlank()) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_phone_number
        } else if (ValidationUtil.mobile(mobile) != ValidationState.VALID) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_phone_number
        }

        updateState {
            it.copy(
                mobile = it.mobile.copy(
                    errorMessage = errorMessage,
                    value = mobile
                )
            )
        }


        return isValid
    }

    private fun validateNationalCode(nationalCode: String): Boolean {
        var isValid = true
        var errorMessage: Int? = null

        if (nationalCode.isBlank()) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_national_id
        } else if (ValidationUtil.nationalCode(nationalCode) != ValidationState.VALID) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_national_id
        }

        updateState {
            it.copy(
                nationalCode = it.nationalCode.copy(
                    errorMessage = errorMessage,
                    value = nationalCode
                )
            )
        }

        return isValid
    }


    private fun validateOTP(otp: String): Boolean {
        var isValid = true
        var errorMessage: Int? = null

        if (otp.isBlank()) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_otp
        }

        updateState {
            it.copy(
                otpModel = it.otpModel.copy(
                    errorMessage = errorMessage,
                )
            )
        }
        return isValid
    }
    //endregion

    private fun navigateToVerifyOtp(mobile: String, traceNumber: String, nationalCode: String) {

        val model = OtpModel().copy(
            mobile = mobile,
            uniqueTraceNumber = traceNumber,
            nationalCode = nationalCode
        )

        navigateTo(
            NavigationCommand.ToWithData(
                ApplicationRoutes.verifyOtpScreenRoute + ApplicationRoutes.loginParam,
                linkedMapOf(Pair(NavigationParam.MOBILE, Gson().toJson(model)))
            )
        )
    }


    private fun verifyOtp(otpModel: OtpModel) {
        Log.d("MAMAD", "${otpModel.otpCode}    ${otpModel.mobile}  ${otpModel.uniqueTraceNumber}")

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
            }
        ) {
            val param = VerifyLoginOtpParam(
                Code = otpModel.otpCode,
                UniqueTraceNumber = otpModel.uniqueTraceNumber,
                Mobile = otpModel.mobile
            )

            verifyOtpUseCase.execute(param)
                .onAboPaySuccess { it ->
                    Log.d("onSuccess****", it?.accessToken + "")

                    updateState {
                        it.copy(loading = true)
                    }
                    it?.let {
                        saveToken(it.accessToken)
                        navigateTo(
                            NavigationCommand.ToScreen(
                                ApplicationRoutes.homeScreenRoute,
                                clearBackStack = true
                            )
                        )
                    }
                }
                .onAboPayException { throwable ->
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                    updateState {
                        it.copy(loading = false)
                    }
                }
                .onAboPayApiError { apiError ->
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} ${apiError.error.message}"))
                    Log.d("onApiErrorHandler****", apiError.toString() + apiError)
                }
        }


    }

    private fun saveToken(token: String) {
        sharedPrefStorage.save(
            StorageKey.TOKEN, token
        )

    }


    //region Timer
    private fun setupTimer(timeMillis: Long) {
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateState {
                    it.copy(
                        otpModel = it.otpModel.copy(
                            timeLeft = FormatUtil.toDualTimeFormat(millisUntilFinished)
                        )
                    )
                }
            }

            override fun onFinish() {
                updateState {
                    it.copy(
                        otpModel = it.otpModel.copy(
                            timeLeft = "درخواست مجدد",
                            enableOtpRequest = true
                        )
                    )
                }

            }
        }

    }

    private fun startTimer() {
        updateState {
            it.copy(
                otpModel = it.otpModel.copy(
                    enableOtpRequest = false,
                    timeMilliSecond = 0L
                )
            )
        }
        timer.start()

    }

    private fun stopTimer() {
        timer.cancel()
        updateState {
            it.copy(
                otpModel = it.otpModel.copy(
                    enableOtpRequest = true
                )
            )
        }
    }
    //endregion

}
