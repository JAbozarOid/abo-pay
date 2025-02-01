package com.jabozaroid.abopay.feature.messenger.model.chats

data class ChatUiModel(
    val profileImageUrl: String? = null,
    val username: String = "",
    val currentMsg: String = "",
    val time: String = "",
    val msgNumber: String = "",
    val isGroup: Boolean = false
)
