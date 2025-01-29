package com.jabozaroid.abopay.feature.payment.pay.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.SwitchComponent
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.CardInformation
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentAction
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentEvent
import com.jabozaroid.abopay.feature.payment.pay.model.PaymentUiModel
import com.jabozaroid.abopay.feature.payment.pay.view.component.ShowCardBottomSheet
import com.jabozaroid.abopay.feature.payment.pay.view.component.WalletContent
import com.jabozaroid.abopay.feature.payment.pay.viewmodel.PaymentViewModel

/**
 *  Created on 8/29/2024 
 **/

class PaymentScreen : BaseScreen<PaymentUiModel, PaymentAction, PaymentEvent>(
    name = "payment",
    route = ApplicationRoutes.paymentScreenRoute
) {
    @Composable
    override fun ViewModel(): PaymentViewModel = hiltViewModel()

    @Composable
    override fun Content(state: PaymentUiModel) {

        val viewModel = ViewModel()

        viewModel.process(PaymentAction.OnUpdatePaymentModel)

        val cardNumber: MutableState<String> = remember { mutableStateOf("") }

        PaymentContent(
            state,
            cardNumber = cardNumber,
            onCardSelected = {
                viewModel.process(PaymentAction.SetCardNumber(it.subTitle, true))
                viewModel.process(PaymentAction.SetCardToken(it.id))
                cardNumber.value = it.subTitle
            },
            onPayButtonClicked = {
                viewModel.process(PaymentAction.PaymentButtonClicked(state.paymentMetaData))
            },
            onOtpRequest = {
                viewModel.process(
                    PaymentAction.SendOtpButtonClicked(
                        state.cardInformation.card
                    )
                )
            },
            onChangeCardTextFiled = {
                viewModel.process(PaymentAction.SetCardNumber(cardNumber.value, false))
                viewModel.process(PaymentAction.SetCardToken(""))
            },
            onChangeOtpTextFiled = {
                viewModel.process(PaymentAction.SetOtp(it))
            },
            onChangeCvv2 = {
                viewModel.process(PaymentAction.SetCvv2(it))
            },
            onChangeMonth = { viewModel.process(PaymentAction.SetMonth(it)) },
            onChangeYear = { viewModel.process(PaymentAction.SetYear(it)) },
            dismissCardBottomSheet = { viewModel.process(PaymentAction.HideCardBottomSheet) },
            onCardIconClick = { viewModel.process(PaymentAction.ShowCardBottomSheet) }
        )


    }


    @Composable
    private fun PaymentContent(
        state: PaymentUiModel,
        cardNumber: MutableState<String> = mutableStateOf(""),
        onCardSelected: (SearchItemModel) -> Unit,
        onPayButtonClicked: () -> Unit,
        onOtpRequest: () -> Unit,
        onChangeCardTextFiled: (String) -> Unit,
        onChangeOtpTextFiled: (String) -> Unit,
        onChangeCvv2: (String) -> Unit,
        onChangeMonth: (String) -> Unit,
        onChangeYear: (String) -> Unit,
        dismissCardBottomSheet: () -> Unit,
        onCardIconClick: () -> Unit,
    ) {


        val showCardPayment = rememberSaveable {
            mutableStateOf(true)
        }


        if (state.cardBottomSheet.isVisible)
            ShowCardBottomSheet(
                list = state.userCards,
                onCardSelected = {
                    onCardSelected(it)
                }, dismiss = {
                    dismissCardBottomSheet()
                }
            )


        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundScreen)
        ) {
            val (toolbar, content, btnConfirm) = createRefs()


            AppToolbar(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(R.string.pay),
                onRightIconClicked = {
                })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = Dimens.size_8,
                        end = Dimens.size_8,
                        bottom = Dimens.size_8,
                    )
                    .clip(RoundedCornerShape(Dimens.size_12))
                    .background(AppTheme.colorScheme.background)
                    .constrainAs(
                        content
                    ) {
                        top.linkTo(toolbar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = fillToConstraints
                    }) {


                SwitchComponent(
                    modifier = Modifier.padding(Dimens.size_10),
                    titles = listOf(
                        aboPayStringResource(R.string.wallet),
                        aboPayStringResource(R.string.payment_with_card)
                    ),
                    onTitleSelected = { index ->
                        showCardPayment.value = index == 1
                    }, initialSelectedIndex = 1
                )

                if (showCardPayment.value) {
                    CardInformation(model = state.cardInformation,
                        onChangeCardTextFiled = {
                            onChangeCardTextFiled(it)
                        },
                        cardNumber = cardNumber,
                        onChangeCvv2 = { onChangeCvv2(it) },
                        onChangeMonth = { onChangeMonth(it) },
                        onChangeYear = { onChangeYear(it) },
                        onIconClicked = { onCardIconClick() },
                        onOtpRequest = { onOtpRequest() },
                        isEnableMonth = state.isDateEnable,
                        isEnableYear = state.isDateEnable,
                        onChangeOtpTextFiled = { onChangeOtpTextFiled(it) })
                } else WalletContent(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                )
            }


            AppButton(
                enabled = if (state.isDateEnable) state.cardInformation.enablePaymentButton()
                else state.cardInformation.enablePaymentButtonWithToken(),
                onClick = {
                    onPayButtonClicked.invoke()
                },
                modifier = Modifier.constrainAs(btnConfirm) {
                    start.linkTo(parent.start, Dimens.size_16)
                    end.linkTo(parent.end, Dimens.size_16)
                    bottom.linkTo(parent.bottom, Dimens.size_16)
                    width = fillToConstraints
                }
            ) {
                Text(
                    text = aboPayStringResource(R.string.pay),
                    style = AppTheme.typography.text_12PX_16SP_M
                )

            }

        }
    }


    @Preview
    @Composable
    fun PreviewPayment() {
        AppTheme {

            AppBackground(modifier = Modifier) {
                PaymentContent(
                    state = PaymentUiModel(),
                    onCardSelected = {},
                    onPayButtonClicked = {},
                    onOtpRequest = {
                    },
                    onChangeCardTextFiled = {},
                    onChangeOtpTextFiled = {},
                    onChangeCvv2 = {},
                    onChangeMonth = {},
                    onChangeYear = {},
                    onCardIconClick = {},
                    dismissCardBottomSheet = {}
                )
            }

        }
    }
}






