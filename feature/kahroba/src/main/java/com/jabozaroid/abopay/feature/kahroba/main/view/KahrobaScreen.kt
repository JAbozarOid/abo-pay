package com.jabozaroid.abopay.feature.kahroba.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.AddUserNewCardBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.cardmanager.SourceCardManager
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes.kahrobaScreenRoute
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaAction
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaEvent
import com.jabozaroid.abopay.feature.kahroba.main.model.KahrobaUiModel
import com.jabozaroid.abopay.feature.kahroba.main.model.SupportingBank
import com.jabozaroid.abopay.feature.kahroba.main.view.bottomsheet.ConfirmOtpBottomSheetComponent
import com.jabozaroid.abopay.feature.kahroba.main.view.bottomsheet.HelperComponentBottomSheet
import com.jabozaroid.abopay.feature.kahroba.main.viewmodel.KahrobaViewModel

class KahrobaScreen : BaseScreen<KahrobaUiModel, KahrobaAction, KahrobaEvent>(
    name = "KahrobaScreen",
    route = kahrobaScreenRoute
) {
    @Composable
    override fun ViewModel(): KahrobaViewModel = hiltViewModel()

    @Composable
    override fun Content(state: KahrobaUiModel) {
        val viewModel = ViewModel()

        KahrobaBaseContent(
            state = state,
            onToolbarRightIconClicked = {
                viewModel.process(KahrobaAction.NavigateUp)
            },
            onHideAddCardBottomSheet = {
                viewModel.process(KahrobaAction.OnHideAddCardBottomSheet)
            },
            onAddBottomSheetConfirmButton = {
                viewModel.process(KahrobaAction.OnHideAddCardBottomSheet)
                viewModel.process(KahrobaAction.OnAddNewSourceCard(state.addCardBottomSheetUiModel.cardInformationUiModel.card))
                viewModel.process(KahrobaAction.OnShowConfirmOtpBottomSheet)
            },
            onAddBottomSheetSourceCardNumberChanged = {
                viewModel.process(KahrobaAction.OnAddSourceCardNumberChange(it))
            },
            onAddBottomSheetSourceCardMonthChanged = {
                viewModel.process(KahrobaAction.OnAddSourceCardMonthChange(it))
            },
            onCardYearChanged = {
                viewModel.process(KahrobaAction.OnAddSourceCardYearChange(it))
            },
            onCardCvv2Changed = {
                viewModel.process(KahrobaAction.OnAddSourceCardCvv2Change(it))
            },
            onDefaultCardStateChange = {
                viewModel.process(KahrobaAction.OnDefaultAddSourceCardStateChanged(it))
            },
            onAddCardIconClicked = {
                viewModel.process(KahrobaAction.OnShowAddCardBottomSheet)
            },
            onSourceCardSelected = {
                viewModel.process(KahrobaAction.OnSourceCardSelected(it))
            },
            onClickConfirmButton = {
                viewModel.process(KahrobaAction.ContinueToPayment(state.selectedCard))
            },
            onDismissConfirmOtpBottomSheet = {
                viewModel.process(KahrobaAction.OnHideConfirmOtpBottomSheet)
            },
            onOtpValueChange = {
                viewModel.process(KahrobaAction.OnChangeOtp(it))
            },
            onVerifyOtpClick = {
                viewModel.process(KahrobaAction.OnConfirmOtpButton(state.selectedCard.otpModel.otpCode))
            },
            onOtpRequestClick = {
                viewModel.process(KahrobaAction.OnOtpRequest)
            },
            onToolbarLeftIconClick = {
                viewModel.process(KahrobaAction.OnShowHelperBottomSheet)
            },
            onCloseHelperBottomSheet = {
                viewModel.process(KahrobaAction.OnHideHelperBottomSheet)
            }
        )
    }
}

@Composable
private fun KahrobaBaseContent(
    state: KahrobaUiModel,
    onToolbarRightIconClicked: () -> Unit = {},
    onHideAddCardBottomSheet: () -> Unit = {},
    onAddBottomSheetConfirmButton: () -> Unit = {},
    onAddBottomSheetSourceCardNumberChanged: (String) -> Unit = {},
    onAddBottomSheetSourceCardMonthChanged: (String) -> Unit = {},
    onCardYearChanged: (String) -> Unit = {},
    onCardCvv2Changed: (String) -> Unit = {},
    onCardOwnerNameChange: (String) -> Unit = {},
    onDefaultCardStateChange: (Boolean) -> Unit = {},
    onAddCardIconClicked: () -> Unit = {},
    onSourceCardSelected: (Card) -> Unit = {},
    onClickConfirmButton: () -> Unit = {},
    onDismissConfirmOtpBottomSheet: () -> Unit = {},
    onOtpRequestClick: () -> Unit = {},
    onStartOtpTimer: () -> Unit = {},
    onOtpValueChange: (String) -> Unit = {},
    onVerifyOtpClick: () -> Unit = {},
    onToolbarLeftIconClick: () -> Unit = {},
    onCloseHelperBottomSheet: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(AppTheme.colorScheme.aboBackgroundScreen)
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

        if (state.confirmOtpBottomSheet.isVisible) {
            ConfirmOtpBottomSheetComponent(
                state = state,
                onDismiss = onDismissConfirmOtpBottomSheet,
                onOtpRequestClicked = onOtpRequestClick,
                onOtpValueChanged = onOtpValueChange,
                onStartOtp = onStartOtpTimer,
                onVerifyOtpButtonClicked = onVerifyOtpClick
            )
        }

        if (state.addCardBottomSheetUiModel.isBottomSheetVisible) {
            AddUserNewCardBottomSheet(
                cardInformationUiModel = state.addCardBottomSheetUiModel.cardInformationUiModel,
                bottomSheetTitle = aboPayStringResource(id = R.string.add_card),
                confirmBtnText = aboPayStringResource(id = R.string.continue_title),
                onHideBottomSheet = onHideAddCardBottomSheet,
                onBtnConfirmClicked = onAddBottomSheetConfirmButton,
                onUserCardNumberChanged = onAddBottomSheetSourceCardNumberChanged,
                onCardMonthChanged = onAddBottomSheetSourceCardMonthChanged,
                onCardYearChanged = onCardYearChanged,
                onCardOwnerNameChanged = onCardOwnerNameChange,
                onDefaultCardStateChange = onDefaultCardStateChange,
                switchTitle = R.string.default_kahroba_card,
                cvv2Enable = true,
                isDescriptionEnable = false,
                isCardDefault = state.addCardBottomSheetUiModel.cardInformationUiModel.card.isDefault,
                onCardCvv2Changed = onCardCvv2Changed,
                isEnableConfirmButton = state.addCardBottomSheetUiModel.cardInformationUiModel.card.enableKahrobaAddUserCardButton()
            )
        }

        Column(modifier = Modifier
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
                top.linkTo(toolBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = fillToConstraints
            }) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.size_12))
                    .padding(horizontal = Dimens.size_4)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = Dimens.size_16)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = aboPayStringResource(R.string.kahroba_supporting_bank_title),
                    style = AppTheme.typography.text_12PX_16SP_M,
                    color = AppTheme.colorScheme.aboOutlineButtonText,
                )
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp
                val itemSize = (screenWidth - 10) / 6
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = Dimens.size_18,
                                end = Dimens.size_4
                            ),
                        horizontalArrangement = Arrangement.spacedBy((4).dp),
                        contentPadding = PaddingValues(Dimens.size_5),

                        ) {
                        items(
                            items = state.supportingBankList,
                            key = { item: SupportingBank -> item.index }) {
                            Box(
                                modifier = Modifier
                                    .size(itemSize.dp, itemSize.dp)
                                    .clip(RoundedCornerShape(Dimens.size_12))
                                    .background(AppTheme.colorScheme.aboBackgroundButton2),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(Dimens.size_25)
                                        .align(Alignment.Center),
                                    painter = painterResource(id = it.logo),
                                    contentDescription = "supporting bank"

                                )
                            }
                        }
                    }

                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = Dimens.size_4)
                        .background(AppTheme.colorScheme.kahrobaDivider)
                        .height(Dimens.size_2)

                )
                Spacer(modifier = Modifier.height(Dimens.size_8))
            }
            if (state.sourceCardList.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.size_18,
                            horizontal = Dimens.size_34
                        ),
                    color = AppTheme.colorScheme.aboOutlineButtonText,
                    textAlign = TextAlign.Center,
                    text = aboPayStringResource(R.string.select_card_title),
                    style = AppTheme.typography.text_12PX_16SP_B

                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.size_18,
                            horizontal = Dimens.size_34
                        ),
                    color = AppTheme.colorScheme.aboOutlineButtonText,
                    textAlign = TextAlign.Center,
                    text = aboPayStringResource(R.string.select_card_description),
                    style = AppTheme.typography.text_11PX_15SP_M
                )
            }


            SourceCardManager(
                modifier = Modifier
                    .fillMaxWidth(),
                cardList = state.sourceCardList,
                onAddCardIconClicked = onAddCardIconClicked,
                onCardSelected = onSourceCardSelected
            )

            if (state.sourceCardList.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.size_18,
                            horizontal = Dimens.size_34
                        ),
                    color = AppTheme.colorScheme.aboOutlineButtonText,
                    textAlign = TextAlign.Center,
                    text = aboPayStringResource(R.string.kahroba_card_description),
                    style = AppTheme.typography.text_12PX_16SP_M

                )
            }
            Spacer(modifier = Modifier.weight(1f))


            AppButton(
                enabled = !state.selectedCard.card.number.isNullOrEmpty(),
                onClick = {
                    onClickConfirmButton()
                },
                modifier = Modifier
                    .padding(Dimens.size_8)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = aboPayStringResource(R.string.continue_title),
                    style = AppTheme.typography.text_12PX_16SP_M
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
        KahrobaBaseContent(state = KahrobaUiModel())
    }

}