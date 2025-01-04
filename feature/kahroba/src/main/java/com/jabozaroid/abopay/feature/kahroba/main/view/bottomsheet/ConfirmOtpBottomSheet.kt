package com.jabozaroid.abopay.feature.kahroba.main.view.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.OTPInput
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaUiModel


@Composable
fun ConfirmOtpBottomSheetComponent(
    state: KahrobaUiModel,
    onStartOtp: () -> Unit = {},
    onOtpValueChanged: (String) -> Unit = {},
    onVerifyOtpButtonClicked: () -> Unit = {},
    onOtpRequestClicked: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {

    BottomSheetComponent(
        title = aboPayStringResource(R.string.enrollment_card),
        content = {
            ConfirmOtpComponent(
                state = state,
                onStartOtp = onStartOtp,
                onOtpValueChanged = onOtpValueChanged,
                onOtpRequestClicked = onOtpRequestClicked,
            )
        },
        onDismiss = onDismiss,
        dualActionBtn = false,
        btn1Text = aboPayStringResource(R.string.confirm),
        onBtn1Click = onVerifyOtpButtonClicked

    )

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun ConfirmOtpComponent(
    state: KahrobaUiModel,
    onStartOtp: () -> Unit,
    onOtpValueChanged: (String) -> Unit,
    onOtpRequestClicked: () -> Unit,
) {
    var otpCode by rememberSaveable { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        val (txtTimer, txtNotice, edtOtp) = createRefs()

            Text(
                text = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.otp_sent),
                color = AppTheme.colorScheme.ivaTitleText,
                style = AppTheme.typography.text_11PX_15SP_B.copy(
                    fontWeight = FontWeight.W500
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(txtNotice) {
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                        top.linkTo(parent.top, Dimens.size_8)
                        width = Dimension.fillToConstraints
                    }

        )
        val maxLength = 6
        OTPInput(
            size = maxLength,
            value = otpCode,
            onValueChange = {
                if (it.length <= maxLength) {
                    otpCode = it
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
                    start.linkTo(parent.start, Dimens.size_18)
                    end.linkTo(parent.end, Dimens.size_18)
                    top.linkTo(edtOtp.bottom, Dimens.size_40)
                    width = Dimension.fillToConstraints
                },
            state = state,
            onOtpRequestClicked = {
                onOtpRequestClicked()
            }
        )

    }

    onStartOtp()
}

@Composable
private fun TimerContent(
    modifier: Modifier,
    state: KahrobaUiModel,
    onOtpRequestClicked: () -> Unit,
) {

    AppOutlinedButton(
        modifier = modifier,
        onClick = {
            if (state.selectedCard.otpModel.enableOtpRequest)
                onOtpRequestClicked()
        },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Black,
            containerColor = AppTheme.colorScheme.ivaTimerBackground
        ),
        borderColor = AppTheme.colorScheme.ivaTimerBackground,
    ) {

        Text(
            text = state.selectedCard.otpModel.timeLeft,
            style = AppTheme.typography.text_9PX_12SP_M.copy(
                fontWeight = FontWeight.W700
            )
        )
    }


}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun ShowScreen() {


    AppTheme {
        AppBackground(modifier = Modifier) {}
        ConfirmOtpComponent(
            state = KahrobaUiModel(),
            {},
            {},
            {},

            )
    }
}

