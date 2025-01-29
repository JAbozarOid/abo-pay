package com.jabozaroid.abopay.feature.cardtocard.receipt.view

import android.app.Activity
import android.graphics.Picture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.wrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.common.util.share.SharingFileType
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.SwitchWithLabel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.cardtocard.KeyValueRow
import com.jabozaroid.abopay.feature.cardtocard.R
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptAction
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptEvent
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptUiModel
import com.jabozaroid.abopay.feature.cardtocard.receipt.viewmodel.ReceiptViewModel
import androidx.compose.material3.HorizontalDivider as HorizontalDivider1

/**
 *  Created on 10/21/2024 
 **/
class ReceiptScreen : BaseScreen<ReceiptUiModel, ReceiptAction, ReceiptEvent>(
    route = ApplicationRoutes.c2cReceiptScreenRoute,
    name = "c2cReceipt"
) {
    @Composable
    override fun ViewModel(): ReceiptViewModel = hiltViewModel()

    @Composable
    override fun Content(state: ReceiptUiModel) {
        val viewModel = ViewModel()

        viewModel.process(ReceiptAction.OnUpdateCardInfo)

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
                    sharingText += state.receiptItemModel.metadataItems.map {
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

            MainContent(state = state,
                onToolbarIconClicked = { viewModel.navigateBack() },
                onButtonDoneClicked = { viewModel.process(ReceiptAction.OnBackButtonClicked) },
                onShareClicked = {
                    sharingFileType = it
                    onShareClicked = true
                })
        }
    }
}

@Composable
private fun MainContent(
    state: ReceiptUiModel,
    onToolbarIconClicked: () -> Unit,
    onButtonDoneClicked: () -> Unit,
    onShareClicked: (SharingFileType) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.aboBackgroundScreen)
    ) {
        val (toolbar, content) = createRefs()
        val (titleContainer, bottomMetaDataContainer, topMetaDataContainer, shareContainer) = createRefs()
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
            toolbarTitle = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.card_to_card),
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

            TopMetaDataContainer(
                modifier = Modifier
                    .padding(
                        vertical = Dimens.size_4,
                        horizontal = Dimens.size_24
                    )
                    .constrainAs(topMetaDataContainer) {
                        top.linkTo(titleContainer.bottom, Dimens.size_8)
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                        bottom.linkTo(bottomMetaDataContainer.top, Dimens.size_8)
                        height = wrapContent
                    }, state = state
            )

            BottomMetaDataContainer(Modifier.constrainAs(bottomMetaDataContainer) {
                top.linkTo(topMetaDataContainer.bottom, Dimens.size_8)
                start.linkTo(parent.start, Dimens.size_8)
                end.linkTo(parent.end, Dimens.size_8)
                bottom.linkTo(shareContainer.top, Dimens.size_8)
                height = fillToConstraints
            }, state)

            ShareContainer(
                modifier = Modifier.constrainAs(shareContainer) {
                    top.linkTo(bottomMetaDataContainer.bottom)
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
                aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.save_in_frequent_transactions),
                checked.value,
                { isChecked ->
                    checked.value = isChecked
                })

            AppButton(
                onClick = { onButtonDoneClicked() },
                modifier = Modifier.constrainAs(btnConfirm) {
                    start.linkTo(parent.start, Dimens.size_16)
                    end.linkTo(parent.end, Dimens.size_16)
                    bottom.linkTo(parent.bottom, Dimens.size_16)
                    width = fillToConstraints
                }
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(text = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.back))
                }
            }

        }

    }

}

@Composable
internal fun TitleReceiptContent(modifier: Modifier, state: ReceiptUiModel) {

    Column(modifier = modifier.padding(all = Dimens.size_8), Arrangement.Center) {

        Image(
            modifier = Modifier
                .height(Dimens.size_64)
                .width(Dimens.size_64)
                .align(alignment = Alignment.CenterHorizontally),
            painter = painterResource(
                // todo : there is condition : if Success : show ic_done else ic_error
                id = R.drawable.ic_done
            ),
            contentDescription = " icon"
        )


        ProvideTextStyle(
            value = AppTheme.typography.text_12PX_16SP_M.copy(
                fontWeight = FontWeight.Bold,
                textDirection = TextDirection.ContentOrRtl
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = Dimens.size_8),

                // todo : there is condition : if Success : show ic_done else ic_error
                text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.successful_card_to_card),
                color = AppTheme.colorScheme.success
            )
        }
    }
}


@Composable
internal fun TopMetaDataContainer(modifier: Modifier, state: ReceiptUiModel) {

    val visualTransformation = remember {
        com.jabozaroid.abopay.core.common.util.CurrencyAmountInputVisualTransformation()
    }
    val transformedText = remember(state.receiptItemModel.amount) {
        visualTransformation.filter(
            AnnotatedString(state.receiptItemModel.amount)
        )
    }.text
    Column(
        modifier = modifier
    ) {
        val price = com.jabozaroid.abopay.core.common.model.MetaDataModel(
            key = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.amount_rial),
            value = transformedText.text
        )
        val date = com.jabozaroid.abopay.core.common.model.MetaDataModel(
            key = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.date_and_time_title),
            value = state.receiptItemModel.dateTime
        )

        KeyValueRow(
            item = price,
            keyTextStyle = AppTheme.typography.text_12PX_16SP_B.copy(
                textDirection = TextDirection.Rtl
            ),
            valueTextStyle = AppTheme.typography.text_12PX_16SP_B.copy(
                fontWeight = FontWeight.Bold,
                textDirection = TextDirection.Ltr
            )
        )

        KeyValueRow(
            item = date,
            keyTextStyle = AppTheme.typography.text_12PX_16SP_B.copy(
                textDirection = TextDirection.Rtl
            ),
            valueTextStyle = AppTheme.typography.text_12PX_16SP_B.copy(
                fontWeight = FontWeight.Bold,
                textDirection = TextDirection.Ltr
            )
        )

    }

}


@Composable
internal fun BottomMetaDataContainer(modifier: Modifier, state: ReceiptUiModel) {

    Column(
        modifier = modifier
    ) {

        HorizontalDivider1(
            modifier = Modifier
                .weight(0.3f)
                .padding(
                    end = Dimens.size_8,
                    start = Dimens.size_24
                )
        )


        LazyColumn(
            modifier = Modifier
                .padding(
                    vertical = Dimens.size_16,
                    horizontal = Dimens.size_24
                )
                .fillMaxHeight(),
        ) {
            items(count = state.receiptItemModel.metadataItems.size) { item ->
                KeyValueRow(item = state.receiptItemModel.metadataItems[item])

            }
        }

        HorizontalDivider1(
            modifier = Modifier
                .weight(0.3f)
                .padding(
                    end = Dimens.size_8,
                    start = Dimens.size_24
                )
        )

    }

}


@Composable
private fun ShareContainer(
    modifier: Modifier,
    onShareClicked: (SharingFileType) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = Dimens.size_4),
        Arrangement.Center,
        Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = Dimens.size_24)
                .height(30.dp)
                .width(30.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onShareClicked(SharingFileType.TEXT)
                },
            alignment = Alignment.CenterEnd,
            painter = painterResource(id = R.drawable.ic_copy),
            contentDescription = " icon"
        )
        Image(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onShareClicked(SharingFileType.IMAGE)
                },
            alignment = Alignment.CenterStart,
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = " icon"
        )
        Image(
            modifier = Modifier
                .padding(start = Dimens.size_24)
                .height(32.dp)
                .width(32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onShareClicked(SharingFileType.PDF)
                },
            alignment = Alignment.CenterStart,
            painter = painterResource(id = R.drawable.ic_share_pdf),
            contentDescription = "share",
        )

    }

}

@Preview
@Composable
internal fun Preview() {

    AppTheme {

        AppBackground(modifier = Modifier) {}
        MainContent(
            state = ReceiptUiModel(),
            onToolbarIconClicked = { },
            onButtonDoneClicked = {}) {

        }
    }

}

