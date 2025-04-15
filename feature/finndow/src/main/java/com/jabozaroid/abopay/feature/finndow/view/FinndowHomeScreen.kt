package com.jabozaroid.abopay.feature.finndow.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.finndow.model.FinndowAction
import com.jabozaroid.abopay.feature.finndow.model.FinndowEvent
import com.jabozaroid.abopay.feature.finndow.model.FinndowUiModel
import com.jabozaroid.abopay.feature.finndow.viewmodel.FinndowViewModel

class FinndowHomeScreen : BaseScreen<FinndowUiModel, FinndowAction, FinndowEvent>(
    route = ApplicationRoutes.finndowHomeScreenRoute, name = "FinndowHomeScreen"
) {
    @Composable
    override fun ViewModel(): FinndowViewModel = hiltViewModel()

    @Composable
    override fun Content(state: FinndowUiModel) {
        val viewModel = ViewModel()
        MainContent(
            onNavigateBack = {
            },
            onShadowingPractices = {
                viewModel.process(action = FinndowAction.OnRequestShadowingPractice)
            })
    }

    @Composable
    private fun MainContent(
        onNavigateBack: () -> Unit = {},
        onShadowingPractices: () -> Unit = {},
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundScreen)
        ) {

            val (toolbarRef, content) = createRefs()
            AppToolbar(
                modifier = Modifier
                    .constrainAs(toolbarRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = Dimens.size_8)
                    .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.finndow_title),
                onRightIconClicked = onNavigateBack)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.size_4,
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
                        height = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }) {

                AppButton(
                    enabled = true,
                    onClick = {
                        onShadowingPractices()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(Dimens.size_12)
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.shadowing_practices))
                    }
                }

            }
        }
    }

    @Preview
    @Composable
    fun PreviewFinndowHomeScreen() {
        MainContent()
    }
}

