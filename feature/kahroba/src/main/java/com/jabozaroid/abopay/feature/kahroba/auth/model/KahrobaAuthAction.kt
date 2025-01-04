package com.jabozaroid.abopay.feature.kahroba.auth.model

import com.jabozaroid.abopay.core.ui.model.IAction

interface KahrobaAuthAction : IAction {

    data object NavigateUp : KahrobaAuthAction

    data class OnNationalCodeChanged(val nationalCode : String) : KahrobaAuthAction
    data class OnPasswordChanged(val password : String) : KahrobaAuthAction
    data class OnConfirmPasswordChanged(val confirmPassword : String,val matchPassword : String) :
        KahrobaAuthAction

    data object OnContinueBtnClicked : KahrobaAuthAction
}