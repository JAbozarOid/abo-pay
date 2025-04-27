package com.jabozaroid.abopay.feature.finndow.viewmodel

import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthAction
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthEvent
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FinndowAuthViewModel @Inject constructor(
) :
    BaseViewModel<FinndowAuthUiModel, FinndowAuthAction, FinndowAuthEvent>(
        initialState = FinndowAuthUiModel()
    ) {

    companion object {
        const val TAG = "FinndowViewModel"
    }

    override val onRefresh: () -> Unit = {}

    override fun handleAction(action: FinndowAuthAction) {

    }




}