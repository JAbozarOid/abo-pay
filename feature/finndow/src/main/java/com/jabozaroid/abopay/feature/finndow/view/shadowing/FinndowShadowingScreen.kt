package com.jabozaroid.abopay.feature.finndow.view.shadowing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthAction
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthEvent
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthUiModel
import com.jabozaroid.abopay.feature.finndow.viewmodel.FinndowAuthViewModel

class FinndowShadowingScreen : BaseScreen<FinndowAuthUiModel, FinndowAuthAction, FinndowAuthEvent>(
    route = ApplicationRoutes.FINNDOW_SHADOWING_SCREEN_ROUTE, name = "FinndowShadowingScreen"
) {
    @Composable
    override fun ViewModel(): FinndowAuthViewModel = hiltViewModel()

    @Composable
    override fun Content(state: FinndowAuthUiModel) {
        val viewModel = ViewModel()
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
            MainContent(
                onContinueBtnClicked = {
                    viewModel.process(FinndowAuthAction.OnContinueClicked)
                }
            )
        }
    }

    @Composable
    private fun MainContent(
        onContinueBtnClicked: () -> Unit = {}
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundScreen)
        ) {

            val (toolbarRef, content) = createRefs()
            AppToolbar(
                rightIcon = null,
                textStyle = TextStyle(
                    fontFamily = AppTheme.typography.text_48PX_24SP_B.fontFamily,
                    fontSize = AppTheme.typography.text_48PX_24SP_B.fontSize
                ),
                modifier = Modifier
                    .constrainAs(toolbarRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = Dimens.size_8)
                    .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(id = R.string.shadowing_courses),
                onRightIconClicked = {}
            )
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
                ProvideTextStyle(value = AppTheme.typography.text_48PX_24SP_B) {
                    Text(
                        aboPayStringResource(id = R.string.shadowing),
                        modifier = Modifier.padding(top = Dimens.size_100)
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_16PX_21SP_B) {
                    Text(
                        aboPayStringResource(id = R.string.create_an_account),
                        modifier = Modifier.padding(top = Dimens.size_100)
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_14PX_19SP_M) {
                    Text(
                        aboPayStringResource(id = R.string.enter_your_email),
                        modifier = Modifier.padding(top = Dimens.size_8)
                    )
                }
                AppTextField(
                    textDirection = TextDirection.Ltr,
                    placeHolder = aboPayStringResource(id = R.string.email_placeholder),
                    placeHolderAlignment = Alignment.CenterStart,
                    onValueChange = {},
                    value = "",
                    modifier = Modifier.padding(
                        top = Dimens.size_8,
                        start = Dimens.size_12,
                        end = Dimens.size_12
                    ),

                    )
                AppButton(
                    enabled = true,
                    onClick = {
                        onContinueBtnClicked()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(Dimens.size_12)
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.continue_btn))
                    }
                }
                OrDivider()
                AppButton(
                    enabled = false,
                    onClick = {
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(Dimens.size_12)
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.continue_with_google))
                    }
                }
                AppButton(
                    enabled = false,
                    onClick = {
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(Dimens.size_12)
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.continue_with_apple))
                    }
                }
                ProvideTextStyle(value = AppTheme.typography.text_11PX_15SP_M) {
                    Text(
                        aboPayStringResource(id = R.string.terms_and_condition),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            top = Dimens.size_8,
                            start = Dimens.size_12,
                            end = Dimens.size_12
                        )
                    )
                }

            }
        }
    }

    @Composable
    fun OrDivider() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.size_12)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.size_1),
                color = Color.Gray
            )
            Text(
                text = "or",
                modifier = Modifier.padding(horizontal = Dimens.size_8),
                color = Color.Gray,
                style = AppTheme.typography.text_8PX_10SP_B
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.size_1),
                color = Color.Gray
            )
        }
    }


    @Preview(showBackground = true)
    @ThemePreviews
    @Composable
    fun PreviewFinndowHomeScreen() {
        AppTheme {
            MainContent()
        }
    }
}

