package com.jabozaroid.abopay.feature.login.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.OTPInput
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.login.R
import com.jabozaroid.abopay.feature.login.model.LoginAction
import com.jabozaroid.abopay.feature.login.model.LoginEvent
import com.jabozaroid.abopay.feature.login.model.LoginUiModel
import com.jabozaroid.abopay.feature.login.model.OtpModel
import com.jabozaroid.abopay.feature.login.viewmodel.LoginViewModel


class VerifyOtpScreen : BaseScreen<LoginUiModel, LoginAction, LoginEvent>(
    name = "verifyOtp",
    route = ApplicationRoutes.verifyOtpScreenRoute + ApplicationRoutes.loginParam
) {
    @Composable
    override fun ViewModel(): LoginViewModel = hiltViewModel()

    @Composable
    override fun Content(state: LoginUiModel) {

        val viewModel = ViewModel()
        lateinit var otpModel: OtpModel

        parameters[NavigationParam.MOBILE]?.let { data ->
            val model = Gson().fromJson(data, OtpModel::class.java)
            if (model != null)
                otpModel = model
        }


        val otpCode = rememberSaveable {
            mutableStateOf("")
        }


        MainContent(otpModel, state, onVerifyOtpButtonClicked = {

            viewModel.process(
                LoginAction.VerifyOtpButtonClicked(
                    otpModel.copy(
                        otpCode = otpCode.value
                    )
                )
            )
        }, onOtpValueChanged = {
            LoginAction.OnOtpValueChanged(otpCode.value)
            state.otpModel.otpCode = otpCode.value
        },
            onStartOtp = {
                if (state.otpModel.timeMilliSecond == -1L) {
                    viewModel.process(LoginAction.OnTimerStarted)
                }
            }, onEditClicked = {
                viewModel.process(LoginAction.OnEditMobileClicked)
            }, onOtpRequestClicked = {
                viewModel.process(
                    LoginAction.SendOtpButtonClicked(
                        otpModel.mobile, otpModel.nationalCode, false
                    )
                )
            }, otpCode = otpCode
        )

    }


    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    private fun MainContent(
        otpModel: OtpModel,
        state: LoginUiModel,
        onStartOtp: () -> Unit,
        onOtpValueChanged: (String) -> Unit,
        onVerifyOtpButtonClicked: () -> Unit,
        onEditClicked: () -> Unit,
        onOtpRequestClicked: () -> Unit,
        otpCode: MutableState<String>
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            val (btnEnter, txtTimer, txtNotice, edtOtp, edtMobile) = createRefs()
            val (toolbarIcon) = createRefs()
            val centerGuidLine = createGuidelineFromTop(.3f)

            Image(
                modifier = Modifier.constrainAs(toolbarIcon) {
                    start.linkTo(parent.start, Dimens.size_8)
                    end.linkTo(parent.end, Dimens.size_8)
                    top.linkTo(parent.top, Dimens.size_40)
                    width = Dimension.fillToConstraints
                },
                painter = painterResource(id = R.drawable.ic_eva), contentDescription = ""
            )


            ProvideTextStyle(
                value = AppTheme.typography.text_10PX_13SP_B.copy(
                    fontWeight = FontWeight.W500
                )
            ) {

                Text(
                    text = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.otp_sent),
                    color = AppTheme.colorScheme.ivaNoticeTextColor,
                    style = AppTheme.typography.text_11PX_15SP_B,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(txtNotice) {
                            start.linkTo(parent.start, Dimens.size_8)
                            end.linkTo(parent.end, Dimens.size_8)
                            top.linkTo(centerGuidLine, Dimens.size_8)
                            width = Dimension.fillToConstraints
                        }

                )
            }


            val maxLength = 6
            OTPInput(
                size = maxLength,
                value = otpCode.value,
                onValueChange = {
                    if (it.length <= maxLength) {
                        otpCode.value = it
                        onOtpValueChanged(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(edtOtp) {
                        top.linkTo(txtNotice.bottom, Dimens.size_34)
                    }
            )


            TimerContent(
                modifier = Modifier
                    .constrainAs(txtTimer) {
                        start.linkTo(btnEnter.start)
                        end.linkTo(btnEnter.end)
                        top.linkTo(edtOtp.bottom, Dimens.size_40)
                        width = Dimension.fillToConstraints
                    },
                state = state,
                onOtpRequestClicked = {
                    onOtpRequestClicked()
                }
            )



            EditContainer(mobile = otpModel.mobile, modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onEditClicked.invoke()
                }
                .clip(RoundedCornerShape(Dimens.size_10))
                .background(color = AppTheme.colorScheme.ivaBackgroundButton2)
                .constrainAs(edtMobile) {
                    start.linkTo(btnEnter.start)
                    end.linkTo(btnEnter.end)
                    top.linkTo(btnEnter.bottom, Dimens.size_40)
                    width = Dimension.wrapContent
                })


            AppPrimaryButton(
                modifier = Modifier
                    .constrainAs(btnEnter) {

                        top.linkTo(txtTimer.bottom, Dimens.size_15)
                        start.linkTo(parent.start, Dimens.size_40)
                        end.linkTo(parent.end, Dimens.size_40)
                        width = Dimension.fillToConstraints

                    },
                enabled = state.isVerifySubmitButtonEnable(),
                onClick = {
                    onVerifyOtpButtonClicked()
                },
                text = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.enter_title)
            )


        }

        onStartOtp()
    }


    @Composable
    private fun TimerContent(
        modifier: Modifier,
        state: LoginUiModel,
        onOtpRequestClicked: () -> Unit,
    ) {

        AppOutlinedButton(
            modifier = modifier,
            onClick = {
                if (state.otpModel.enableOtpRequest)
                    onOtpRequestClicked()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black,
                containerColor = AppTheme.colorScheme.ivaTimerBackground
            ),
            borderColor = AppTheme.colorScheme.ivaTimerBackground,
        ) {

            ProvideTextStyle(
                value = AppTheme.typography.text_9PX_12SP_M.copy(
                    fontWeight = FontWeight.W700
                )
            ) {
                Text(text = state.otpModel.timeLeft)
            }
        }


    }


    @Composable
    private fun EditContainer(mobile: String, modifier: Modifier) {

        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            ProvideTextStyle(
                value = AppTheme.typography.text_10PX_13SP_M.copy(
                    fontWeight = FontWeight.W500
                )
            ) {
                Text(
                    text = mobile,
                    color = AppTheme.colorScheme.ivaTitleText,
                    modifier = Modifier.padding(all = Dimens.size_10),
                )
            }

            Image(
                modifier = Modifier.padding(all = Dimens.size_8),
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = ""
            )
        }

    }


    @SuppressLint("UnrememberedMutableState")
    @Preview
    @Composable
    private fun ShowScreen() {


        AppTheme {
            AppBackground(modifier = Modifier) {}
            MainContent(OtpModel(), state = LoginUiModel(), {}, {}, {}, {}, {}, mutableStateOf(""))

        }
    }

}








