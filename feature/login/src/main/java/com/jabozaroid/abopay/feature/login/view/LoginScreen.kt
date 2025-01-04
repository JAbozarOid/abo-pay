package com.jabozaroid.abopay.feature.login.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.login.model.LoginAction
import com.jabozaroid.abopay.feature.login.model.LoginEvent
import com.jabozaroid.abopay.feature.login.model.LoginUiModel
import com.jabozaroid.abopay.feature.login.model.MobileModel
import com.jabozaroid.abopay.feature.login.view.bottomsheet.ShowReagentBottomSheet
import com.jabozaroid.abopay.feature.login.viewmodel.LoginViewModel


class LoginScreen : BaseScreen<LoginUiModel, LoginAction, LoginEvent>(
    name = "login", route = ApplicationRoutes.loginScreenRoute
) {
    @Composable
    override fun ViewModel(): LoginViewModel = hiltViewModel()

    @Composable
    override fun Content(state: LoginUiModel) {

        val viewModel = ViewModel()

        var showBottomSheet by remember {
            mutableStateOf(false)
        }

        if (showBottomSheet) {
            ShowReagentBottomSheet {
                showBottomSheet = false
            }
        }

        MainContent(state,
            onNationalCodeValueChanged = { nationalCode ->
                viewModel.process(
                    LoginAction.OnNationalCodeChanged(nationalCode)
                )

            }, onMobileValueChanged = { mobile ->
                viewModel.process(
                    LoginAction.OnMobileValueChanged(mobile)
                )
            }, onReagentClick = {
                showBottomSheet = true
            },
            onSubmitClicked = {
                viewModel.process(
                    LoginAction.SendOtpButtonClicked(
                        state.mobile.value,
                        state.nationalCode.value,
                        true
                    )
                )
            })
    }

    @Composable
    private fun MainContent(
        state: LoginUiModel, onNationalCodeValueChanged: (String) -> Unit,
        onMobileValueChanged: (String) -> Unit,
        onReagentClick: () -> Unit, onSubmitClicked: () -> Unit,
    ) {


        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            val (indicator, rulesContainer, otpContainer) = createRefs()
            val (pager, toolbarIcon) = createRefs()
            val centerGuideLine = createGuidelineFromTop(0.45f)


            val pagerState =
                rememberPagerState(initialPage = 0, pageCount = { state.introItems.size })

            Image(
                modifier = Modifier
                    .padding(top = Dimens.size_24, bottom = Dimens.size_16)
                    .constrainAs(toolbarIcon)
                    {
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                        top.linkTo(parent.top, Dimens.size_8)
                        width = Dimension.fillToConstraints
                    },
                painter = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.abo_pay_logo),
                contentDescription = ""
            )



            IntroSlider(modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(toolbarIcon.bottom, Dimens.size_8)
                    start.linkTo(parent.start, Dimens.size_4)
                    end.linkTo(parent.end, Dimens.size_4)
                    bottom.linkTo(indicator.top, Dimens.size_4)
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize(),
                state = state, pagerState = pagerState)


            PageIndicator(
                numberOfPages = pagerState.pageCount, modifier = Modifier
                    .constrainAs(indicator) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(centerGuideLine, Dimens.size_16)
                    }, selectedPage = pagerState.settledPage
            )


            OtpContainer(state,
                modifier = Modifier
                    .constrainAs(otpContainer) {
                        start.linkTo(parent.start, Dimens.size_15)
                        end.linkTo(parent.end, Dimens.size_15)
                        top.linkTo(centerGuideLine, Dimens.size_5)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = Dimens.size_8),
                onMobileValueChanged = {
                    onMobileValueChanged(it)
                },
                onNationalCodeValueChanged = {
                    onNationalCodeValueChanged(it)
                },
                onSubmitClicked = {
                    onSubmitClicked.invoke()
                })

            Box(
                modifier = Modifier
                    .constrainAs(rulesContainer) {
                        start.linkTo(parent.start, Dimens.size_15)
                        end.linkTo(parent.end, Dimens.size_15)
                        top.linkTo(otpContainer.bottom, Dimens.size_40)
                        bottom.linkTo(parent.bottom, Dimens.size_8)
                        width = Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.Center
            ) {
                AppOutlinedButton(
                    modifier = Modifier.height(Dimens.size_36),
                    onClick = { onReagentClick.invoke() },
                    enabled = true,
                    borderColor = AppTheme.colorScheme.ivaTextFieldHint,
                    contentPadding = ButtonDefaults.TextButtonContentPadding,
                    text = {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = Dimens.size_34),
                            text = aboPayStringResource(R.string.enter_reagent_code),
                            color = AppTheme.colorScheme.ivaTitleText,
                            style = AppTheme.typography.text_9PX_12SP_B.copy(
                                textAlign = TextAlign.Center
                            )
                        )

                    })

            }
        }

    }

    @Composable
    private fun OtpContainer(
        state: LoginUiModel,
        modifier: Modifier,
        onMobileValueChanged: (String) -> Unit,
        onNationalCodeValueChanged: (String) -> Unit,
        onSubmitClicked: () -> Unit,
    ) {

        Column(modifier = modifier) {
            val nationalCodeMaxChar = 10
            AppTextField(
                textDirection = TextDirection.Ltr,
                value = state.nationalCode.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    if (it.length <= nationalCodeMaxChar) {
                        onNationalCodeValueChanged.invoke(it)
                    }

                },
                isError = !state.nationalCode.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = state.nationalCode.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(id = R.string.national_code),
                placeHolder = aboPayStringResource(id = R.string.place_holder_national_code),
                modifier = Modifier.fillMaxWidth()

            )

            val mobileMaxChar = 11
            AppTextField(
                textDirection = TextDirection.Ltr,
                value = state.mobile.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    if (it.length <= mobileMaxChar) {
                        onMobileValueChanged(it)

                    }

                },
                isError = !state.mobile.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = state.mobile.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(id = R.string.phone_number),
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                placeHolder = aboPayStringResource(id = R.string.place_holder_phone_number),
                cursorColor = AppTheme.colorScheme.ivaTextFieldHint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.size_14, bottom = Dimens.size_24)

            )


            AppPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = aboPayStringResource(R.string.send_otp),
                enabled = state.isLoginSubmitButtonEnable(),
                onClick = {
                    onSubmitClicked.invoke()

                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.size_16),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = aboPayStringResource(id = R.string.app_rules_extra),
                    style = AppTheme.typography.text_12PX_16SP_M.copy(
                        fontWeight = FontWeight.W500
                    ),
                    color = AppTheme.colorScheme.ivaDetailText
                )

                Spacer(modifier = Modifier.width(Dimens.size_4))

                Text(
                    modifier = Modifier
                        .clickable {
                            //TODO:Move to web rule page
                        },
                    text = aboPayStringResource(id = R.string.app_rules),
                    style = AppTheme.typography.text_12PX_16SP_M.copy(
                        fontWeight = FontWeight.W700
                    ),
                    color = AppTheme.colorScheme.ivaIconToolbar
                )

            }


        }


    }


    @Preview(showBackground = true)
    @Composable
    @DevicePreviews
    fun ShowPage() {
        AppTheme {
            val state = LoginUiModel(
                mobile = MobileModel()
            )
            AppBackground(modifier = Modifier) {
                MainContent(state, {}, {}, {}, {})
            }

        }
    }

}




