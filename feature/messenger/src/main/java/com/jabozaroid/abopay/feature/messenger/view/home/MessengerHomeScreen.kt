package com.jabozaroid.abopay.feature.messenger.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.jabozaroid.abopay.feature.messenger.viewmodel.MessengerHomeViewModel

class MessengerHomeScreen :
    BaseScreen<MessengerHomeUiModel, MessengerHomeAction, MessengerHomeEvent>(
        route = ApplicationRoutes.messengerScreenRoute, name = "HomeMessengerScreen"
    ) {

    @Composable
    override fun ViewModel(): MessengerHomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: MessengerHomeUiModel) {
        MainContent(state)
    }


}

@Composable
internal fun MainContent(state: MessengerHomeUiModel) {
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
            MessengerTabComponent(titles = listOf("CHATS", "STATUS", "CALLS"))
        }

    }
}

@Preview(showBackground = true)
@ThemePreviews
@Composable
internal fun PreviewMessengerHomeContent() {
    AppTheme {
        MainContent(MessengerHomeUiModel())
    }
}