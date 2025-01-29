package com.jabozaroid.abopay.feature.messenger.viewmodel

import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.messenger.model.home.HomeMessengerAction
import com.jabozaroid.abopay.feature.messenger.model.home.HomeMessengerEvent
import com.jabozaroid.abopay.feature.messenger.model.home.HomeMessengerUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeMessengerViewModel @Inject constructor() :
    BaseViewModel<HomeMessengerUiModel, HomeMessengerAction, HomeMessengerEvent>(initialState = HomeMessengerUiModel()) {


    override fun handleAction(action: HomeMessengerAction) {
        TODO("Not yet implemented")
    }

    override val onRefresh: () -> Unit
        get() = TODO("Not yet implemented")
}