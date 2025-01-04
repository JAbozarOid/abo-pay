package com.jabozaroid.abopay.feature.cardtocard.confirmation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.DottedShape
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.cardtocard.KeyValueRow
import com.jabozaroid.abopay.feature.cardtocard.c2c.view.bottomSheet.CardToCardPaymentBottomSheet
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.ConfirmationAction
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.ConfirmationEvent
import com.jabozaroid.abopay.feature.cardtocard.confirmation.model.ConfirmationUiModel
import com.jabozaroid.abopay.feature.cardtocard.confirmation.viewmodel.ConfirmationViewModel

/**
 *  Created on 10/21/2024 
 **/
class ConfirmationScreen : BaseScreen<ConfirmationUiModel, ConfirmationAction, ConfirmationEvent>(
    route = ApplicationRoutes.c2cConfrimationScreenRoute,
    name = "c2cConfirmation"
) {
    @Composable
    override fun ViewModel(): ConfirmationViewModel = hiltViewModel()

    @Composable
    override fun Content(state: ConfirmationUiModel) {
        val viewModel = ViewModel()

        viewModel.process(ConfirmationAction.OnUpdateCardInfo)

        HomeContent(state = state, onConfirmButtonClicked = {
            viewModel.process(ConfirmationAction.OnConfirmButtonClicked)
        }, onCancelButtonClicked = {
            viewModel.process(ConfirmationAction.OnCancelButtonClicked)

        }, onToolbarIconClicked = {
            viewModel.process(ConfirmationAction.OnToolbarBackClicked)
        })

        if (state.cardPaymentBottomSheet.isBottomSheetVisible) {
            CardToCardPaymentBottomSheet(
                state = state.cardPaymentBottomSheet.cardInformationUiModel,
                bottomSheetTitle = aboPayStringResource(id = R.string.pay),
                confirmBtnText = aboPayStringResource(id = R.string.confirm),
                onChangeCvv2 = { viewModel.process(ConfirmationAction.SetCvv2(it)) },
                onChangeOtpTextFiled = { viewModel.process(ConfirmationAction.SetOtp(it)) },
                onChangeMonth = { viewModel.process(ConfirmationAction.SetMonth(it)) },
                onChangeYear = { viewModel.process(ConfirmationAction.SetYear(it)) },
                onOtpRequest = { viewModel.process(ConfirmationAction.SendOtpButtonClicked) },
                onBtnConfirmClicked = { viewModel.process(ConfirmationAction.OnPaymentButtonClicked) },
                onHideBottomSheet = { viewModel.process(ConfirmationAction.OnHidePaymentBottomSheet) }

            )
        }
    }


    @Composable
    internal fun HomeContent(
        state: ConfirmationUiModel,
        onConfirmButtonClicked: () -> Unit,
        onCancelButtonClicked: () -> Unit,
        onToolbarIconClicked: () -> Unit
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.ivaBackgroundScreen)
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
                toolbarTitle = aboPayStringResource(id = R.string.c2c_confirmation),
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


                    TitleContent(modifier = Modifier.constrainAs(titleContainer) {
                        bottom.linkTo(topGuideline, Dimens.size_8)
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                    })

                    MetaDataContainer(Modifier.constrainAs(metaDataContainer) {
                        top.linkTo(titleContainer.bottom, Dimens.size_8)
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                        height = fillToConstraints
                    }, state)

                    ButtonContent(
                        modifier = Modifier.constrainAs(btnConfirm) {
                            start.linkTo(parent.start, Dimens.size_16)
                            end.linkTo(parent.end, Dimens.size_16)
                            bottom.linkTo(parent.bottom, Dimens.size_16)
                            width = fillToConstraints
                        }, onCancelButtonClicked, onConfirmButtonClicked
                    )
                }


            }
        }
    }


    @Composable
    private fun ButtonContent(
        modifier: Modifier,
        onCancelButtonClicked: () -> Unit,
        onConfirmButtonClicked: () -> Unit
    ) {
        Row(modifier = modifier, Arrangement.SpaceBetween) {
            AppOutlinedButton(
                onClick = {
                    onCancelButtonClicked()
                },
                text = {
                    Text(
                        text = aboPayStringResource(R.string.cancel),
                        style = AppTheme.typography.text_11PX_15SP_B
                    )
                }, modifier = Modifier
                    .weight(0.45f)
                    .padding(horizontal = Dimens.size_8)
            )
            AppPrimaryButton(
                modifier = Modifier.weight(0.75f),
                onClick = {
                    onConfirmButtonClicked()
                },

                text = aboPayStringResource(R.string.confirm)
            )
        }

    }


    @Composable
    private fun TitleContent(modifier: Modifier) {

        Column(modifier = modifier.padding(all = Dimens.size_8), Arrangement.Center) {
            ProvideTextStyle(
                value = AppTheme.typography.text_14PX_19SP_M.copy(
                    fontWeight = FontWeight.Bold,
                    textDirection = TextDirection.ContentOrRtl
                )
            ) {

                val painter =
                    painterResource(id = com.jabozaroid.abopay.feature.cardtocard.R.drawable.ic_c2c)
                Image(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    painter = painter,
                    contentDescription = " icon"
                )

                Spacer(modifier = Modifier.padding(Dimens.size_4))
                Text(
                    modifier = Modifier.padding(vertical = Dimens.size_15),
                    text = aboPayStringResource(R.string.agree_c2c_rules),
                    style = AppTheme.typography.text_12PX_16SP_B,
                    color = AppTheme.colorScheme.ivaTitleText
                )
            }


        }

    }


    @Composable
    private fun MetaDataContainer(modifier: Modifier, state: ConfirmationUiModel) {

        Column(
            modifier = modifier
        ) {
            Box(
                Modifier
                    .height(Dimens.size_1)
                    .padding(horizontal = Dimens.size_16)
                    .fillMaxWidth()
                    .background(
                        color = AppTheme.colorScheme.ivaTitleText,
                        shape = DottedShape(step = Dimens.size_10)
                    )
            )

            DestinationInfo(
                modifier = Modifier
                    .padding(horizontal = Dimens.size_8)
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_8,
                        vertical = Dimens.size_10
                    )
                    .padding(Dimens.size_8)
                    .fillMaxWidth(), state = state
            )

            Box(
                Modifier
                    .height(Dimens.size_1)
                    .padding(horizontal = Dimens.size_16)
                    .fillMaxWidth()
                    .background(
                        color = AppTheme.colorScheme.ivaTitleText,
                        shape = DottedShape(step = Dimens.size_10)
                    )
            )


            PriceContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_16,
                        vertical = Dimens.size_16
                    )
                    .padding(Dimens.size_8)
                    .fillMaxWidth(),
                state = state
            )

            Box(
                Modifier
                    .height(Dimens.size_1)
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.size_16)
                    .background(
                        color = AppTheme.colorScheme.ivaTitleText,
                        shape = DottedShape(step = Dimens.size_10)
                    )
            )
        }

    }

    @Composable
    private fun PriceContainer(modifier: Modifier, state: ConfirmationUiModel) {
        val visualTransformation = remember {
            com.jabozaroid.abopay.core.common.util.CurrencyAmountInputVisualTransformation()
        }
        val transformedText = remember(state.transferModel.amount) {
            visualTransformation.filter(
                AnnotatedString(state.transferModel.amount)
            )
        }.text
        Row(
            modifier = modifier, Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier.padding(all = Dimens.size_5),
                text = transformedText ,
                style = AppTheme.typography.text_12PX_16SP_M.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppTheme.colorScheme.ivaTitleText
            )

            Text(
                style = AppTheme.typography.text_12PX_16SP_M.copy(
                    fontWeight = FontWeight.Bold
                ),
                text = aboPayStringResource(id = R.string.amount_in_rial),
                modifier = Modifier.padding(all = Dimens.size_5),
                color = AppTheme.colorScheme.ivaTitleText,
            )

        }

    }

    @Composable
    private fun DestinationInfo(modifier: Modifier, state: ConfirmationUiModel) {
        Column(
            modifier = modifier) {
            val sourcePan = com.jabozaroid.abopay.core.common.model.MetaDataModel(
                key = aboPayStringResource(id = R.string.source_card_number),
                value = state.transferModel.sourcePan
            )

            val targetPan = com.jabozaroid.abopay.core.common.model.MetaDataModel(
                key = aboPayStringResource(id = R.string.target_card_number),
                value = state.transferModel.targetPan
            )
            val targetOwner = com.jabozaroid.abopay.core.common.model.MetaDataModel(
                key = aboPayStringResource(id = R.string.target_card_owner),
                value = state.transferModel.targetPanOwnerName
            )

            KeyValueRow(item = sourcePan)
            KeyValueRow(item = targetOwner)
            KeyValueRow(item = targetPan)
        }
    }


    @Preview
    @Composable
    internal fun Preview() {

        AppTheme {

            AppBackground(modifier = Modifier) {}
            HomeContent(state = ConfirmationUiModel(), onConfirmButtonClicked = { }, {}, {})
        }
    }
}