package com.jabozaroid.abopay.feature.messenger.view.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.R
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.messenger.MessengerToolbar
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeAction
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeEvent
import com.jabozaroid.abopay.feature.messenger.model.home.MessengerHomeUiModel
import com.jabozaroid.abopay.feature.messenger.viewmodel.MessengerHomeViewModel

class MessengerDetailScreen :
    BaseScreen<MessengerHomeUiModel, MessengerHomeAction, MessengerHomeEvent>(
        route = ApplicationRoutes.messengerDetailScreenRoute, name = "MessengerDetailScreen"
    ) {
    @Composable
    override fun ViewModel(): MessengerHomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: MessengerHomeUiModel) {
        MainContent(state)
    }


}

@Composable
fun MainContent(state: MessengerHomeUiModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                AppTheme.colorScheme.aboBackgroundScreen
            )
    ) {

        val (toolbarRef, content) = createRefs()
        MessengerToolbar(modifier = Modifier
            .constrainAs(
                toolbarRef
            ) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth(), title = "PayMessenger")
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.size_8, end = Dimens.size_8, bottom = Dimens.size_8
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
                }) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.aboLightGrayBackground)
            ) {
                val (chats, inputChat) = createRefs()

                Row(
                    modifier = Modifier.constrainAs(inputChat) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AppTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dimens.size_4),
                        value = "",
                        onValueChange = {},
                        placeHolder = "Type a message"
                    )
                    Box(
                        modifier = Modifier
                            .size(Dimens.size_40)
                            .clip(CircleShape)
                            .background(Color.Green), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .size(Dimens.size_34)
                                .padding(Dimens.size_4),
                            painter = painterResource(id = R.drawable.ic_mic),
                            contentDescription = "Profile Image",
                        )
                    }
                }
            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessengerDetail() {
    MainContent(state = MessengerHomeUiModel())
}