package com.jabozaroid.abopay.feature.finndow.view.finndow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_0
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.finndow.model.finndow.FinndowAction
import com.jabozaroid.abopay.feature.finndow.model.finndow.FinndowEvent
import com.jabozaroid.abopay.feature.finndow.model.finndow.FinndowUiModel
import com.jabozaroid.abopay.feature.finndow.view.bottomsheet.FinndowHomeBottomNavigation
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
            onShadowingPractices = {
                viewModel.process(action = FinndowAction.OnRequestShadowingPractice)
            })
    }

    @Composable
    private fun MainContent(
        onShadowingPractices: () -> Unit = {},
    ) {
        Scaffold(
            containerColor = AppTheme.colorScheme.aboBackgroundScreen,
            topBar = {
                AppToolbar(
                    rightIcon = null,
                    textStyle = TextStyle(
                        fontFamily = AppTheme.typography.text_48PX_24SP_B.fontFamily,
                        fontSize = AppTheme.typography.text_48PX_24SP_B.fontSize
                    ),
                    modifier = Modifier
                        .padding(top = Dimens.size_8)
                        .fillMaxWidth(),
                    toolbarTitle = aboPayStringResource(id = R.string.finndow_title),
                    onRightIconClicked = {}
                )
            },
            bottomBar = { FinndowHomeBottomNavigation() }
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(
                        size_0,
                        padding.calculateTopPadding(),
                        size_0,
                        padding.calculateBottomPadding()
                    )
            )
            {

                //region shadowing practices
                AppButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.finndowBlueLight,
                        contentColor = AppTheme.colorScheme.aboWhiteBackground
                    ),
                    enabled = true,
                    onClick = {
                        onShadowingPractices()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.size_50, end = Dimens.size_50,
                        )
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.shadowing_practices))
                    }
                }
                //endregion

                //region exam mode
                AppButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.finndowOrangeDark,
                        contentColor = AppTheme.colorScheme.aboWhiteBackground
                    ),
                    enabled = true,
                    onClick = {
                        onShadowingPractices()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.size_50,
                            end = Dimens.size_50,
                            top = Dimens.size_25,
                            bottom = Dimens.size_25
                        )
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.exam_mode))
                    }
                }
                //endregion

                //region ai subject preparation
                AppButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.finndowRedBlue,
                        contentColor = AppTheme.colorScheme.aboWhiteBackground
                    ),
                    enabled = true,
                    onClick = {
                        onShadowingPractices()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.size_50, end = Dimens.size_50,
                            bottom = Dimens.size_25
                        )
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.ai_subject_preparation))
                    }
                }
                //endregion

                //region progress
                AppButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.finndowGreen,
                        contentColor = AppTheme.colorScheme.aboWhiteBackground
                    ),
                    enabled = true,
                    onClick = {
                        onShadowingPractices()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.size_50, end = Dimens.size_50,
                            bottom = Dimens.size_25
                        )
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.progress))
                    }
                }
                //endregion

            }
        }


    }

    @Preview
    @Composable
    fun PreviewFinndowHomeScreen() {
        AppTheme {
            MainContent()
        }
    }
}

