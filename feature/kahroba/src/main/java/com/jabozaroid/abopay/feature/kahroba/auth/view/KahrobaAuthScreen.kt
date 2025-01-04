package com.jabozaroid.abopay.feature.kahroba.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes.kahrobaAuthScreenRoute
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.kahroba.auth.model.KahrobaAuthAction
import com.jabozaroid.abopay.feature.kahroba.auth.model.KahrobaAuthEvent
import com.jabozaroid.abopay.feature.kahroba.auth.model.KahrobaAuthUiModel
import com.jabozaroid.abopay.feature.kahroba.auth.viewmodel.KahrobaAuthViewModel

class KahrobaAuthScreen : BaseScreen<KahrobaAuthUiModel, KahrobaAuthAction, KahrobaAuthEvent>(
    name = "KahrobaAuthScreen", route = kahrobaAuthScreenRoute
) {
    @Composable
    override fun ViewModel(): KahrobaAuthViewModel = hiltViewModel()

    @Composable
    override fun Content(state: KahrobaAuthUiModel) {
        val viewModel = ViewModel()
        MainContent(state, onToolbarBackIconClicked = {
            viewModel.process(KahrobaAuthAction.NavigateUp)
        },
            onNationalCodeValueChanged = { nationalCode ->
                viewModel.process(KahrobaAuthAction.OnNationalCodeChanged(nationalCode))
            },
            onPasswordValueChanged = { password ->
                viewModel.process(KahrobaAuthAction.OnPasswordChanged(password))
            },
            onConfirmPasswordChanged = { confirmPassword, matchPassword ->
                viewModel.process(
                    KahrobaAuthAction.OnConfirmPasswordChanged(
                        confirmPassword,
                        matchPassword
                    )
                )
            },
            onContinueBtnClicked = {
                viewModel.process(KahrobaAuthAction.OnContinueBtnClicked)
            }
        )
    }
}

@Composable
fun MainContent(
    state: KahrobaAuthUiModel,
    onToolbarBackIconClicked: () -> Unit = {},
    onNationalCodeValueChanged: (String) -> Unit = {},
    onPasswordValueChanged: (String) -> Unit = {},
    onConfirmPasswordChanged: (String, String) -> Unit = { _, _ -> },
    onContinueBtnClicked: () -> Unit,
) {
    val matchPassword = remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

            .background(
                AppTheme.colorScheme.ivaBackgroundScreen
            )
    ) {
        val (toolbar, content) = createRefs()
        AppToolbar(modifier = Modifier
            .constrainAs(toolbar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = Dimens.size_8)
            .fillMaxWidth(),
            toolbarTitle = aboPayStringResource(R.string.kahroba_title_payment),
            onRightIconClicked = onToolbarBackIconClicked)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = Dimens.size_8,
                    end = Dimens.size_8,
                    bottom = Dimens.size_8,
                )
                .clip(RoundedCornerShape(12.dp))
                .background(AppTheme.colorScheme.background)
                .constrainAs(
                    content
                ) {
                    top.linkTo(toolbar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = fillToConstraints
                },
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        top = Dimens.size_24, bottom = Dimens.size_24
                    )
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .size(100.dp),
                painter = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_kahroba),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(8.dp))

            // national code
            val nationalCodeMaxChar = 10
            AppTextField(
                value = state.nationalCode.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    if (it.length <= nationalCodeMaxChar) {
                        onNationalCodeValueChanged(it)
                    }

                },
                isError = !state.nationalCode.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = state.nationalCode.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(id = R.string.national_code),
                placeHolder = "نمونه :  2220072991",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.size_20, end = Dimens.size_20)

            )
            Spacer(modifier = Modifier.height(8.dp))

            // password
            val passwordMaxChar = 8
            AppTextField(
                textAlign = TextAlign.Center,
                value = state.password.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    if (it.length <= passwordMaxChar) {
                        matchPassword.value = it
                        onPasswordValueChanged(it)
                    }

                },
                isError = !state.password.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = state.password.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(id = R.string.password),
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                placeHolder = aboPayStringResource(id = R.string.place_holder_password),
                cursorColor = AppTheme.colorScheme.ivaTextFieldHint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.size_20, end = Dimens.size_20)

            )
            Spacer(modifier = Modifier.height(8.dp))

            // confirm password
            AppTextField(
                value = state.confirmPassword.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    if (it.length <= passwordMaxChar) {
                        onConfirmPasswordChanged(it, matchPassword.value)
                    }

                },
                isError = !state.confirmPassword.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = state.confirmPassword.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(id = R.string.confirm_password),
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                cursorColor = AppTheme.colorScheme.ivaTextFieldHint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.size_20, end = Dimens.size_20)

            )

            Spacer(modifier = Modifier.weight(1f))
            AppPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimens.size_20,
                        end = Dimens.size_20,
                        bottom = Dimens.size_20,
                        top = Dimens.size_20
                    ),
                text = aboPayStringResource(R.string.continue_title),
                enabled = state.isContinueBtnEnable(),
                onClick = {
                    onContinueBtnClicked()
                },
            )


        }
    }
}


@Preview
@Composable
@ThemePreviews
@DevicePreviews
fun PreviewKahrobaAuthScreen() {
    MainContent(state = KahrobaAuthUiModel(),
        onToolbarBackIconClicked = {},
        onNationalCodeValueChanged = {},
        onPasswordValueChanged = {},
        onConfirmPasswordChanged = { _, _ -> },
        onContinueBtnClicked = {}
    )
}