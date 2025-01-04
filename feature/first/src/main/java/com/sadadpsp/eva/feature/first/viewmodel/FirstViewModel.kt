package com.jabozaroid.abopay.feature.first.viewmodel

import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.first.model.FirstAction
import com.jabozaroid.abopay.feature.first.model.FirstEvent
import com.jabozaroid.abopay.feature.first.model.FirstUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor() : BaseViewModel<FirstUiModel, FirstAction, FirstEvent>(
    initialState = FirstUiModel()
) {
    override fun handleAction(action: FirstAction) {
        when (action) {
            FirstAction.btnClicked -> {

            }
        }
    }
}