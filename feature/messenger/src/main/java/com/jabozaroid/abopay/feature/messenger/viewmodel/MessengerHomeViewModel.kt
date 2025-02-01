package com.jabozaroid.abopay.feature.messenger.viewmodel

import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.messenger.model.chats.ChatUiModel
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeAction
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeEvent
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessengerHomeViewModel @Inject constructor() :
    BaseViewModel<MessengerHomeUiModel, MessengerHomeAction, MessengerHomeEvent>(initialState = MessengerHomeUiModel()) {


    override fun handleAction(action: MessengerHomeAction) {
        when (action) {
            is MessengerHomeAction.OnChatItemClicked -> {
                navigateToMessengerDetailScreen(action.chatUiModel)
            }
        }
    }

    private fun navigateToMessengerDetailScreen(chatUiModel: ChatUiModel) {
        navigateTo(command = NavigationCommand.ToScreen(route = ApplicationRoutes.messengerDetailScreenRoute))
    }

    override val onRefresh: () -> Unit
        get() = TODO("Not yet implemented")
}