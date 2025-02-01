package com.jabozaroid.abopay.feature.messenger.view.chats

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.feature.messenger.model.chats
import com.jabozaroid.abopay.feature.messenger.model.chats.ChatUiModel

@Composable
fun ChatScreen(chats: List<ChatUiModel>, onChatItemClicked: (ChatUiModel) -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(chats) { index, item ->
            ChatCard(
                item, onItemClicked = {
                    onChatItemClicked(item)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen(
        chats
    ) {

    }
}