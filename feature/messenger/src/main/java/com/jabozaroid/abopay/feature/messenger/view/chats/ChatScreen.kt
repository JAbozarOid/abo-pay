package com.jabozaroid.abopay.feature.messenger.view.chats

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreen(chats: List<ChatUiModel>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(chats) { index, item ->
            ChatCard(
                item
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen(
        listOf(
            ChatUiModel(
                profileImageUrl = null,
                username = "Abozar rbdt",
                lastMsg = "Please send me your id",
                msgDate = "Yesterday",
                msgNumber = "10"
            ),
            ChatUiModel(
                profileImageUrl = null,
                username = "+98912895589",
                lastMsg = "school time is on 7:00",
                msgDate = "1/29/2025",
                msgNumber = "5"
            ),
            ChatUiModel(
                profileImageUrl = null,
                username = "04408965987",
                lastMsg = "Please send me your id",
                msgDate = "Yesterday",
                msgNumber = "56"
            ),
            ChatUiModel(
                profileImageUrl = null,
                username = "Eli.m",
                lastMsg = "Meeting room c.215",
                msgDate = "2/5/2023",
                msgNumber = "170"
            ),
        )
    )
}