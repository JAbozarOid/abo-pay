package com.jabozaroid.abopay.feature.payment.reciept.view

import android.app.Activity
import android.graphics.Picture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.wrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.common.util.share.SharingFileType
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.SwitchWithLabel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptAction
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptEvent
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptUiModel
import com.jabozaroid.abopay.feature.payment.reciept.preview.receiptUiModel
import com.jabozaroid.abopay.feature.payment.reciept.view.component.MetaDataReceiptContainer
import com.jabozaroid.abopay.feature.payment.reciept.view.component.ShareContainer
import com.jabozaroid.abopay.feature.payment.reciept.view.component.TitleReceiptContent
import com.jabozaroid.abopay.feature.payment.reciept.viewmodel.ReceiptViewModel
import com.jabozaroid.abopay.feature.payment.share.SharingReceipt

/**
 *  Created on 8/28/2024 
 **/
class ReceiptScreen : BaseScreen<ReceiptUiModel, ReceiptAction, ReceiptEvent>(
    name = "receipt",
    route = ApplicationRoutes.paymentReceiptScreenRoute
) {
    @Composable
    override fun ViewModel(): ReceiptViewModel = hiltViewModel()

    @Composable
    override fun Content(state: ReceiptUiModel) {

        val viewModel = ViewModel()

        viewModel.process(ReceiptAction.OnUpdateReceiptModel)


        var onShareClicked by rememberSaveable {
            mutableStateOf(false)
        }
        var sharingFileType by rememberSaveable {
            mutableStateOf(SharingFileType.PDF)
        }

        Box {
            val context = LocalContext.current as Activity
            val configuration = LocalConfiguration.current
            val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
            val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
            val picture = remember { Picture() }
            SharingReceipt(state = state, picture)
            if (onShareClicked) {
                if (sharingFileType == SharingFileType.TEXT) {
                    var sharingText = ""
                    sharingText += state.receiptModel.visualItems.map {
                        "${it.key} : ${it.value}"
                    }
                    viewModel.process(
                        ReceiptAction.SharePlainReceipt(
                            context,
                            sharingText
                        )
                    )
                } else {
                    viewModel.process(
                        ReceiptAction.ShareReceipt(
                            context,
                            picture,
                            screenWidth,
                            screenHeight,
                            sharingFileType
                        )
                    )
                }
                onShareClicked = false
            }

            PaymentReceiptContent(
                state = state,
                onToolbarIconClicked = { viewModel.navigateBack() },
                { viewModel.process(ReceiptAction.ReceiptButtonDoneClicked) },
                onShareClicked = {
                    sharingFileType = it
                    onShareClicked = true
                })
        }
    }
}

@Composable
private fun PaymentReceiptContent(
    state: ReceiptUiModel,
    onToolbarIconClicked: () -> Unit,
    onButtonDoneClicked: () -> Unit,
    onShareClicked: (SharingFileType) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.ivaBackgroundScreen)
    ) {
        val (toolbar, content) = createRefs()
        val (titleContainer, metaDataContainer, shareContainer) = createRefs()
        val (btnConfirm, switch) = createRefs()

        AppToolbar(
            modifier = Modifier
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(Dimens.size_4)
                .fillMaxWidth(),
            toolbarTitle = state.receiptModel.commonItems.toolbarTitle,
            onRightIconClicked = {
                onToolbarIconClicked()
            })

        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = Dimens.size_4,
                start = Dimens.size_8,
                end = Dimens.size_8,
                bottom = Dimens.size_4
            )
            .clip(RoundedCornerShape(Dimens.size_12))
            .background(AppTheme.colorScheme.background)
            .constrainAs(content) {
                top.linkTo(toolbar.bottom)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                height = fillToConstraints
            }) {

            TitleReceiptContent(state = state, modifier = Modifier.constrainAs(titleContainer) {
                top.linkTo(parent.top, Dimens.size_34)
                start.linkTo(parent.start, Dimens.size_8)
                end.linkTo(parent.end, Dimens.size_8)
            })

            MetaDataReceiptContainer(Modifier.constrainAs(metaDataContainer) {
                top.linkTo(titleContainer.bottom, Dimens.size_8)
                start.linkTo(parent.start, Dimens.size_8)
                end.linkTo(parent.end, Dimens.size_8)
                bottom.linkTo(shareContainer.top, Dimens.size_8)
                height = fillToConstraints
            }, state)

            ShareContainer(
                modifier = Modifier.constrainAs(shareContainer) {
                    top.linkTo(metaDataContainer.bottom)
                    bottom.linkTo(switch.top, Dimens.size_58)
                    start.linkTo(parent.start, Dimens.size_8)
                    end.linkTo(parent.end, Dimens.size_8)
                    height = wrapContent
                },
                onShareClicked = onShareClicked
            )


            val checked = rememberSaveable { mutableStateOf(true) }

            SwitchWithLabel(
                modifier = Modifier
                    .constrainAs(switch)
                    {
                        end.linkTo(parent.end, Dimens.size_16)
                        bottom.linkTo(btnConfirm.top)
                        width = fillToConstraints
                    }
                    .padding(bottom = Dimens.size_24),
                aboPayStringResource(id = R.string.save_in_frequent_transactions),
                checked.value,
                { isChecked ->
                    checked.value = isChecked
                })


            AppPrimaryButton(onClick = { onButtonDoneClicked() },
                text = aboPayStringResource(R.string.back),
                modifier = Modifier.constrainAs(btnConfirm) {
                    start.linkTo(parent.start, Dimens.size_16)
                    end.linkTo(parent.end, Dimens.size_16)
                    bottom.linkTo(parent.bottom, Dimens.size_16)
                    width = fillToConstraints
                })

        }

    }

}


@Preview
@Composable
fun Preview() {

    AppTheme {

        AppBackground(modifier = Modifier) {

            PaymentReceiptContent(
                receiptUiModel, {}, {}, {})

        }
    }
}
