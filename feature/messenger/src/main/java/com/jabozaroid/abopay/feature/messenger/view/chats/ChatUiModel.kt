package com.jabozaroid.abopay.feature.messenger.view.chats

data class ChatUiModel(
    val profileImageUrl: String? = null,
    val username: String = "",
    val lastMsg: String = "",
    val msgDate: String = "",
    val msgNumber: String = ""
)
