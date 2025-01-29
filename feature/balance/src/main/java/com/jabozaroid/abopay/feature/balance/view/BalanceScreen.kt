package com.jabozaroid.abopay.feature.balance.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.AddUserNewCardBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.CardInformation
import com.jabozaroid.abopay.core.designsystem.component.cardmanager.SourceCardManager
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.balance.model.BalanceAction
import com.jabozaroid.abopay.feature.balance.model.BalanceEvent
import com.jabozaroid.abopay.feature.balance.model.BalanceUiModel
import com.jabozaroid.abopay.feature.balance.view.componen.BalanceDescription
import com.jabozaroid.abopay.feature.balance.view.componen.BalanceReceiptContentBody
import com.jabozaroid.abopay.feature.balance.viewmodel.BalanceViewModel

class BalanceScreen : BaseScreen<BalanceUiModel, BalanceAction, BalanceEvent>(
    name = "balance",
    route = ApplicationRoutes.balanceScreenRoute
) {
    @Composable
    override fun ViewModel(): BalanceViewModel = hiltViewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content(state: BalanceUiModel) {
        val viewModel = ViewModel()

        BalanceContent(
            modifier = Modifier.fillMaxWidth(), state = state,
            onAddCardIconClicked = {
                viewModel.process(BalanceAction.OnShowAddCardBottomSheet)
            },
            onSourceCardSelected = {
                viewModel.process(BalanceAction.OnSourceCardSelected(it))
            },
            onToolbarIconClicked = {
                viewModel.process(BalanceAction.NavigateUp)
            },
            onCloseDescription = { false },
            onClickConfirmButton = {
                viewModel.process(BalanceAction.GetBalanceInquiry(state.selectedCard))
            },
            onChangeCvv2 = {
                viewModel.process(BalanceAction.SetCvv2(it))
            },
            onOtpRequest = {
                viewModel.process(
                    BalanceAction.SendOtpButtonClicked(
                        card = state.selectedCard.card.copy(
                            number = state.selectedCard.card.number,
                            token = ""
                        )
                    )
                )
            },
            onChangeOtpTextFiled = {
                viewModel.process(BalanceAction.SetOtp(it))
            },
            balanceReceiptOnBackClick = {
                viewModel.process(BalanceAction.HideBalanceReceipt)
            },
            onHideAddCardBottomSheet = {
                viewModel.process(BalanceAction.OnHideAddCardBottomSheet)
            },
            onAddBottomSheetConfirmButton = {
                viewModel.process(BalanceAction.OnHideAddCardBottomSheet)
                viewModel.process(BalanceAction.OnAddNewSourceCard(state.addCardBottomSheetUiModel.cardInformationUiModel.card))
            },
            onAddBottomSheetSourceCardNumberChanged = {
                viewModel.process(BalanceAction.OnAddSourceCardNumberChange(it))
            },
            onAddBottomSheetSourceCardMonthChanged = {
                viewModel.process(BalanceAction.OnAddSourceCardMonthChange(it))
            },
            onCardYearChanged = {
                viewModel.process(BalanceAction.OnAddSourceCardYearChange(it))
            },
            onCardOwnerNameChange = {
                viewModel.process(BalanceAction.OnAddSourceCardOwnerNameChanged(it))
            },
            onDefaultCardStateChange = {
                viewModel.process(BalanceAction.OnDefaultAddSourceCardStateChanged(it))
            },
        )

    }

    @Composable
    fun BalanceContent(
        modifier: Modifier = Modifier,
        state: BalanceUiModel,
        onToolbarIconClicked: () -> Unit = {},
        onAddCardIconClicked: () -> Unit = {},
        onSourceCardSelected: (Card) -> Unit = {},
        onCloseDescription: () -> Boolean = { false },
        onClickConfirmButton: () -> Unit,
        onChangeOtpTextFiled: (secondPassword: String) -> Unit,
        onChangeCvv2: (String) -> Unit,
        onOtpRequest: () -> Unit = {},
        balanceReceiptOnBackClick: () -> Unit,
        onHideAddCardBottomSheet: () -> Unit,
        onAddBottomSheetConfirmButton: () -> Unit,
        onAddBottomSheetSourceCardNumberChanged: (String) -> Unit,
        onAddBottomSheetSourceCardMonthChanged: (String) -> Unit,
        onCardYearChanged: (String) -> Unit,
        onCardOwnerNameChange: (String) -> Unit,
        onDefaultCardStateChange: (Boolean) -> Unit,

        ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(AppTheme.colorScheme.aboBackgroundScreen)

        ) {
            val (toolbar, content, receiptContent) = createRefs()
            AppToolbar(modifier = Modifier
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = Dimens.size_8)
                .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(R.string.balanceTitle),
                onRightIconClicked = onToolbarIconClicked)

            if (state.addCardBottomSheetUiModel.isBottomSheetVisible) {
                AddUserNewCardBottomSheet(
                    cardInformationUiModel = state.addCardBottomSheetUiModel.cardInformationUiModel,
                    bottomSheetTitle = aboPayStringResource(id = R.string.add_card),
                    onHideBottomSheet = onHideAddCardBottomSheet,
                    onBtnConfirmClicked = {
                        onAddBottomSheetConfirmButton()
                    },
                    confirmBtnText = aboPayStringResource(id = R.string.add),
                    onUserCardNumberChanged = {
                        onAddBottomSheetSourceCardNumberChanged(it)
                    },
                    onCardMonthChanged = {
                        onAddBottomSheetSourceCardMonthChanged(it)
                    },
                    onCardYearChanged = {
                        onCardYearChanged(it)
                    },
                    onCardOwnerNameChanged = {
                        onCardOwnerNameChange(it)
                    },
                    onDefaultCardStateChange = {
                        onDefaultCardStateChange(it)
                    },
                )
            }

            if (state.isShowReceipt) {
                BalanceReceiptContentBody(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(
                            receiptContent
                        ) {
                            top.linkTo(toolbar.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            height = fillToConstraints
                        },
                    state = state,
                    onClickConfirmButton = {
                        balanceReceiptOnBackClick()
                    }
                )

            } else {
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
                        top.linkTo(toolbar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = fillToConstraints
                    }) {

                    SourceCardManager(
                        modifier = Modifier
                            .fillMaxWidth(),
                        cardList = state.sourceCardList,
                        onAddCardIconClicked = onAddCardIconClicked,
                        onCardSelected = onSourceCardSelected
                    )
                    CardInformation(
                        model = state.selectedCard,
                        modifier = Modifier.fillMaxWidth(),
                        isEnableCardNumber = false,
                        onChangeOtpTextFiled = onChangeOtpTextFiled,
                        onChangeCvv2 = {
                            onChangeCvv2(it)
                        },
                        onOtpRequest = onOtpRequest,
                        isEnableOtpBox = true,
                        isEnableCVV2 = true,
                        isEnableYear = false,
                        isEnableMonth = false,

                        )


                    BalanceDescription(modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.size_16),
                        state = "144 تومان",
                        onClose = {
                            onCloseDescription()
                        })

                    Spacer(modifier = Modifier.weight(1f))
                    AppButton(
                        enabled = state.selectedCard.enableBalanceConfirmButton(),
                        onClick = {
                            onClickConfirmButton()
                        },
                        modifier = Modifier
                            .padding(Dimens.size_8)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = aboPayStringResource(R.string.inquiry),
                            style = AppTheme.typography.text_12PX_16SP_M
                        )
                    }
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
            AppBackground(modifier = Modifier) {}
            BalanceContent(
                modifier = Modifier,
                onClickConfirmButton = {},
                onSourceCardSelected = {},
                onChangeCvv2 = {},
                onOtpRequest = {},
                onChangeOtpTextFiled = {},
                balanceReceiptOnBackClick = {},
                state = BalanceUiModel(),
                onToolbarIconClicked = {},
                onAddCardIconClicked = {},
                onCloseDescription = { return@BalanceContent false },
                onHideAddCardBottomSheet = {},
                onAddBottomSheetConfirmButton = {},
                onAddBottomSheetSourceCardNumberChanged = {},
                onAddBottomSheetSourceCardMonthChanged = {},
                onCardYearChanged = {},
                onCardOwnerNameChange = {},
                onDefaultCardStateChange = { it }
            )
        }
    }
}