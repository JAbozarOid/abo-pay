package com.jabozaroid.abopay.feature.finndow.model.auth

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface FinndowAuthAction : IAction {
    data object OnContinueClicked : FinndowAuthAction
}