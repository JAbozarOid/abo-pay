package com.jabozaroid.abopay.feature.kahroba.nfc.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardplaceholder.CardPlaceHolder
import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes.nfcScreenRoute
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.kahroba.main.view.bottomsheet.HelperComponentBottomSheet
import com.jabozaroid.abopay.feature.kahroba.nfc.model.NFCAction
import com.jabozaroid.abopay.feature.kahroba.nfc.model.NFCEvent
import com.jabozaroid.abopay.feature.kahroba.nfc.model.NFCUiModel
import com.jabozaroid.abopay.feature.kahroba.nfc.viewmodel.NFCViewModel

class NFCScreen : BaseScreen<NFCUiModel, NFCAction, NFCEvent>(
    name = "NFCScreen",
    route = nfcScreenRoute
) {
    @Composable
    override fun ViewModel(): NFCViewModel = hiltViewModel<NFCViewModel>()

    @Composable
    override fun Content(state: NFCUiModel) {
        val viewModel = ViewModel()
        NFCContent(state = state,
            onToolbarRightIconClicked = {
                viewModel.process(NFCAction.NavigateUp)
            },
            onToolbarLeftIconClick = {
                viewModel.process(NFCAction.OnShowHelperBottomSheet)
            },
            onCloseHelperBottomSheet = {
                viewModel.process(NFCAction.OnHideHelperBottomSheet)
            })
    }

}

@Composable
private fun NFCContent(
    state: NFCUiModel,
    onToolbarRightIconClicked: () -> Unit = {},
    onToolbarLeftIconClick: () -> Unit = {},
    onCloseHelperBottomSheet: () -> Unit = {},
    ) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(AppTheme.colorScheme.ivaBackgroundScreen)
    ) {
        val (toolBar, content) = createRefs()

        AppToolbar(modifier = Modifier
            .constrainAs(toolBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = Dimens.size_8)
            .fillMaxWidth(),
            toolbarTitle = aboPayStringResource(R.string.kahroba_title_payment),
            onRightIconClicked = onToolbarRightIconClicked,
            leftIcon = com.jabozaroid.abopay.core.designsystem.R.drawable.info,
            onLeftIconClicked = onToolbarLeftIconClick
        )

        if (state.helperBottomSheet.isVisible) {
            HelperComponentBottomSheet(
                onClickUnderstandButton = onCloseHelperBottomSheet,
                helperItem = state.helperBottomSheet.items
            )
        }

        Column(modifier = Modifier
            .fillMaxSize()
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
                top.linkTo(toolBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = fillToConstraints
            })
        {
            CardPlaceHolder(
                modifier = Modifier.padding(
                    horizontal = Dimens.size_24,
                    vertical = Dimens.size_4
                ),
                bankName = state.card.bankName,
                ownerName = state.card.ownerName,
                year = state.card.year.number,
                month = state.card.month.number,
                cardNumber = state.card.number,
                scale = 1f,
                cardColorDown = state.card.colorDown,
                cardColorUp = state.card.colorUp,
                bankIcon = state.card.icon
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.size_24,
                        vertical = Dimens.size_4
                    )
                    .background(AppTheme.colorScheme.kahrobaDivider)
                    .height(2.dp)

            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.size_34),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.size_20,
                                vertical = Dimens.size_8
                            )
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AppTheme.colorScheme.background),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(com.jabozaroid.abopay.feature.kahroba.R.mipmap.kahroba_pose_i),
                        contentDescription = "helperImage"
                    )
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = aboPayStringResource(R.string.nfc_title_description),
                    color = AppTheme.colorScheme.ivaTitleText,
                    style = AppTheme.typography.text_11PX_15SP_M.copy(
                        fontWeight = FontWeight(700),

                        ),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.size_58,
                            vertical = Dimens.size_8
                        ),
                    text = aboPayStringResource(R.string.nfc_description),
                    color = AppTheme.colorScheme.ivaTitleText,
                    style = AppTheme.typography.text_11PX_15SP_B.copy(
                        fontWeight = FontWeight(500)
                    ),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Preview
@Composable
@ThemePreviews
@DevicePreviews
fun Preview() {
    AppTheme {
        NFCContent(
            state = NFCUiModel(
                card = Card(
                    ownerName = "محمد حسینی",
                    number = "6037997175607630",
                    token = "1",
                    cvv2 = CVV2(number = "456"),
                    month = Month(number = "09"),
                    year = Year("1409"),
                    bankName = R.string.melli_bank,
                    colorUp = R.color.melli_up,
                    colorDown = R.color.melli_Down,
                    icon = R.drawable.card_icon_white_melli,
                    isDefault = true
                )
            )
        )
    }

}