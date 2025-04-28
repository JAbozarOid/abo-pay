package com.jabozaroid.abopay.feature.finndow.model.home

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface FinndowHomeAction : IAction {
    data object OnRequestShadowingPractice : FinndowHomeAction
    data object OnShadowingPracticeBtnClicked : FinndowHomeAction
}