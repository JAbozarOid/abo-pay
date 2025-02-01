package com.jabozaroid.abopay.feature.messenger.model.home

import com.jabozaroid.abopay.core.ui.model.IAction
import com.jabozaroid.abopay.feature.messenger.model.chats.ChatUiModel

sealed interface MessengerHomeAction : IAction {

    data class OnChatItemClicked(val chatUiModel: ChatUiModel) : MessengerHomeAction
}