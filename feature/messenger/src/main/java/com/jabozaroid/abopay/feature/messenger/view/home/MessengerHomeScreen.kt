package com.jabozaroid.abopay.feature.messenger.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.messenger.MessengerTabComponent
import com.jabozaroid.abopay.core.designsystem.component.messenger.MessengerToolbar
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeAction
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeEvent
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeUiModel
import com.jabozaroid.abopay.feature.messenger.view.chats.ChatScreen
import com.jabozaroid.abopay.feature.messenger.model.chats
import com.jabozaroid.abopay.feature.messenger.model.chats.ChatUiModel
import com.jabozaroid.abopay.feature.messenger.viewmodel.MessengerHomeViewModel

class MessengerHomeScreen :
    BaseScreen<MessengerHomeUiModel, MessengerHomeAction, MessengerHomeEvent>(
        route = ApplicationRoutes.messengerScreenRoute, name = "HomeMessengerScreen"
    ) {

    @Composable
    override fun ViewModel(): MessengerHomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: MessengerHomeUiModel) {
        val viewModel = ViewModel()
        MainContent(state, onChatItemClicked = { chatModel ->
            viewModel.process(action = MessengerHomeAction.OnChatItemClicked(chatModel))
        })
    }


}

@Composable
internal fun MainContent(state: MessengerHomeUiModel, onChatItemClicked: (ChatUiModel) -> Unit) {

    var selectedItem by remember { mutableIntStateOf(0) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                AppTheme.colorScheme.aboBackgroundScreen
            )
    ) {
        val (toolbarRef, content) = createRefs()
        MessengerToolbar(
            modifier = Modifier
                .constrainAs(
                    toolbarRef
                ) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(), title = "PayMessenger"
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.size_8,
                    end = Dimens.size_8,
                    bottom = Dimens.size_8
                )
                .clip(RoundedCornerShape(Dimens.size_12))
                .background(AppTheme.colorScheme.background)
                .constrainAs(
                    content
                ) {
                    top.linkTo(toolbarRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            MessengerTabComponent(
                titles = listOf("", "CHATS", "STATUS", "CALLS"),
                onTitleSelected = { index ->
                    selectedItem = index
                })

            when (selectedItem) {
                0 -> {
                    //todo : camera
                }

                1 -> {
                    ChatScreen(chats) {
                        onChatItemClicked(it)
                    }
                }

            }
        }

    }
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
internal fun PreviewMessengerHomeContent() {
    AppTheme {
        MainContent(MessengerHomeUiModel()) {}
    }
}