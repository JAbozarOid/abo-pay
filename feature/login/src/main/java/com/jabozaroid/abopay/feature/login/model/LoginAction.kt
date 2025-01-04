package com.jabozaroid.abopay.feature.login.model

import com.jabozaroid.abopay.core.ui.model.IAction


sealed interface LoginAction : IAction {

    data object OnTimerStarted : LoginAction
    data object OnEditMobileClicked : LoginAction
    data class OnMobileValueChanged(val mobile: String) : LoginAction
    data class OnNationalCodeChanged(val nationalCode : String) : LoginAction
    data class SendOtpButtonClicked(val mobile : String , val nationalCode: String, val needValidation: Boolean) :
        LoginAction

    data class OnOtpValueChanged(val otpCode: String) : LoginAction
    data class VerifyOtpButtonClicked(val otpModel: OtpModel) : LoginAction

}