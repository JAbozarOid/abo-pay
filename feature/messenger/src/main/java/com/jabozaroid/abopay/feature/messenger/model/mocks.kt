package com.jabozaroid.abopay.feature.messenger.model

import com.jabozaroid.abopay.feature.messenger.model.chats.ChatUiModel


val chats: List<ChatUiModel> =
    listOf(
        ChatUiModel(
            profileImageUrl = null,
            username = "Abozar rbdt",
            currentMsg = "Please send me your id",
            time = "Yesterday",
            isGroup = false,
            msgNumber = "10"
        ),
        ChatUiModel(
            profileImageUrl = null,
            username = "+98912895589",
            currentMsg = "school time is on 7:00",
            isGroup = false,
            time = "1/29/2025",
            msgNumber = "5"
        ),
        ChatUiModel(
            profileImageUrl = null,
            username = "04408965987",
            currentMsg = "Please send me your id",
            isGroup = true,
            time = "Yesterday",
            msgNumber = "56"
        ),
        ChatUiModel(
            profileImageUrl = null,
            username = "Eli.m",
            isGroup = true,
            currentMsg = "Meeting room c.215",
            time = "2/5/2023",
            msgNumber = "170"
        ),
    )