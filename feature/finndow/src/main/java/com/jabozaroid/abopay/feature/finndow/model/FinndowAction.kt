package com.jabozaroid.abopay.feature.finndow.model

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface FinndowAction : IAction {
    data object OnRequestShadowingPractice : FinndowAction
}