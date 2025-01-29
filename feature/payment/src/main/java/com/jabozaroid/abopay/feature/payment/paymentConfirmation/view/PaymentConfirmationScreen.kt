package com.jabozaroid.abopay.feature.payment.paymentConfirmation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.model.PaymentConfirmationAction
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.model.PaymentConfirmationUiModel
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.preview.paymentConfirmationModel
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.view.component.ButtonContent
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.view.component.MetaDataContainer
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.view.component.TitleContent
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.viewmodel.PaymentConfirmationViewModel
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptEvent


/**
 *  Created on 8/28/2024 
 **/
class PaymentConfirmationScreen :
    BaseScreen<PaymentConfirmationUiModel, PaymentConfirmationAction, ReceiptEvent>(
        name = "payment",
        route = ApplicationRoutes.paymentConfirmationScreenRoute + ApplicationRoutes.paymentConfirmationParam
    ) {
    @Composable
    override fun ViewModel(): PaymentConfirmationViewModel = hiltViewModel()

    @Composable
    override fun Content(state: PaymentConfirmationUiModel) {

        val viewModel = ViewModel()

        lateinit var confirmationModel: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel

        parameters[NavigationParam.PAYMENT_CONFIRMATION_MODEL]?.let { data ->
            val model = Gson().fromJson(data, com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel::class.java)
            if (model != null)
                confirmationModel = model
        }

        viewModel.process(PaymentConfirmationAction.OnUpdateConfirmationModel(confirmationModel))

        PaymentConfirmationContent(
            state,
            onConfirmButtonClicked = {
                viewModel.process(
                    PaymentConfirmationAction.ConfirmButtonClicked(
                        confirmationModel
                    )
                )
            },
            onCancelButtonClicked = { viewModel.process(PaymentConfirmationAction.OnCancelButtonClicked) },
            onToolbarIconClicked = {
                viewModel.navigateBack()
            })

    }
}

@Composable
fun PaymentConfirmationContent(
    state: PaymentConfirmationUiModel,
    onConfirmButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onToolbarIconClicked: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.aboBackgroundScreen)
    ) {
        val (toolbar, content, buttonRow) = createRefs()

        AppToolbar(
            modifier = Modifier
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            toolbarTitle = state.paymentConfirmationModel.commonItems.toolbarTitle,
            onRightIconClicked = { onToolbarIconClicked.invoke() })


        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.size_4,
                    start = Dimens.size_8,
                    end = Dimens.size_8,
                    bottom = Dimens.size_4
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

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (titleContainer, metaDataContainer) = createRefs()
                val topGuideline = createGuidelineFromTop(0.3f)


                TitleContent(state = state, modifier = Modifier.constrainAs(titleContainer) {
                    bottom.linkTo(topGuideline, Dimens.size_8)
                    start.linkTo(parent.start, Dimens.size_8)
                    end.linkTo(parent.end, Dimens.size_8)
                })
                MetaDataContainer(Modifier.constrainAs(metaDataContainer) {
                    top.linkTo(titleContainer.bottom, Dimens.size_8)
                    start.linkTo(parent.start, Dimens.size_8)
                    end.linkTo(parent.end, Dimens.size_8)
                    bottom.linkTo(parent.bottom, Dimens.size_8)
                    height = fillToConstraints
                }, state)


            }

        }
        ButtonContent(
            modifier = Modifier.constrainAs(buttonRow) {
                start.linkTo(parent.start, Dimens.size_16)
                end.linkTo(parent.end, Dimens.size_16)
                bottom.linkTo(parent.bottom, Dimens.size_16)
                width = fillToConstraints
            }, onCancelButtonClicked, onConfirmButtonClicked
        )
    }

}

@Preview
@Composable
fun Preview() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            PaymentConfirmationContent(
                PaymentConfirmationUiModel(
                    paymentConfirmationModel = paymentConfirmationModel
                ), {}, {}, {})

        }

    }
}






