package com.jabozaroid.abopay.feature.cardtocard.c2c.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.AddUserNewCardBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.cardmanager.SourceCardManager
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardAction
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardEvent
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel
import com.jabozaroid.abopay.feature.cardtocard.c2c.view.bottomSheet.CardsDestinationBottomSheet
import com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c.ButtonBox
import com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c.C2CInputValueContent
import com.jabozaroid.abopay.feature.cardtocard.c2c.viewmodel.CardToCardViewModel

/**
 *  Created on 9/29/2024
 **/
class CardToCardScreen : BaseScreen<CardToCardUiModel, CardToCardAction, CardToCardEvent>(
    route = ApplicationRoutes.cardToCardScreenRoute, name = "CardToCardScreen"
) {

    @Composable
    override fun ViewModel(): CardToCardViewModel = hiltViewModel()


    @Composable
    override fun Content(state: CardToCardUiModel) {
        val viewModel = ViewModel()

        val destinationCardNumber: MutableState<String> = remember { mutableStateOf("") }

        MainContent(state,
            onCardValueChanged = {
                viewModel.process(CardToCardAction.SetTargetCardNumber(destinationCardNumber.value))
            },
            onPriceValueChanged = {
                viewModel.process(CardToCardAction.SetAmount(it))
            },
            onDescriptionValueChanged = {
                viewModel.process(CardToCardAction.OnDescriptionValueChanged(it))
            },
            onSaveSwitchStateChanged = {
                viewModel.process(CardToCardAction.OnSaveStateChanged(it))
            },
            onConfirmButtonClicked = {
                viewModel.process(CardToCardAction.OnConfirmButtonClicked)
            },
            onCardIconClicked = {
                viewModel.process(CardToCardAction.ShowTargetCardBottomSheet(true))
            },
            onTargetCardSelected = {
                viewModel.process(CardToCardAction.SetTargetCardNumber(it.subTitle))
                viewModel.process(CardToCardAction.OnSaveStateChanged(false))
                //TODO:Call SetTokenAction
                destinationCardNumber.value = it.subTitle
            },
            dismissCardBottomSheet = {
                viewModel.process(
                    CardToCardAction.ShowTargetCardBottomSheet(
                        false
                    )
                )
            },
            cardNumber = destinationCardNumber,
            onToolbarIconClicked = {
                viewModel.process(CardToCardAction.OnToolbarIconClicked)
            },
            onSourceCardSelected = {
                viewModel.process(CardToCardAction.OnSourceCardSelected(it))
            },
            onAddCardIconClicked = { viewModel.process(CardToCardAction.OnShowAddCardBottomSheet) },
            onDeleteItemClicked = {
                viewModel.process(CardToCardAction.OnCardItemDelete(it))
            },
            onClearCardClicked = {
                viewModel.process(CardToCardAction.OnClearCardClicked(destinationCardNumber.value){
                    destinationCardNumber.value = ""
                })
            }
        )

        if (state.addCardBottomSheetUiModel.isBottomSheetVisible) {
            AddUserNewCardBottomSheet(
                cardInformationUiModel = state.addCardBottomSheetUiModel.cardInformationUiModel,
                bottomSheetTitle = aboPayStringResource(id = R.string.add_card),
                onHideBottomSheet = { viewModel.process(CardToCardAction.OnHideAddCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardToCardAction.OnHideAddCardBottomSheet)
                    viewModel.process(CardToCardAction.OnUserCardAdded(state.addCardBottomSheetUiModel.cardInformationUiModel.card))
                },
                confirmBtnText = aboPayStringResource(id = R.string.add),
                onUserCardNumberChanged = {
                    viewModel.process(CardToCardAction.OnUserCardNumberChanged(it))
                },
                onCardMonthChanged = {
                    viewModel.process(CardToCardAction.OnUserCardMonthChanged(it))
                },
                onCardYearChanged = {
                    viewModel.process(CardToCardAction.OnUserCardYearChanged(it))
                },
                onCardOwnerNameChanged = {
                    viewModel.process(CardToCardAction.OnUserCardOwnerNameChanged(it))
                },
                onDefaultCardStateChange = {
                    viewModel.process(
                        CardToCardAction.OnDefaultCardStateChanged(
                            it
                        )
                    )
                },
                scanCardVisibility = state.scanCardVisibility
            )
        }
    }

    @Composable
    fun MainContent(
        state: CardToCardUiModel,
        onCardValueChanged: (String) -> Unit = {},
        onPriceValueChanged: (String) -> Unit = {},
        onDescriptionValueChanged: (String) -> Unit = {},
        onSaveSwitchStateChanged: (Boolean) -> Unit = {},
        onConfirmButtonClicked: () -> Unit = {},
        onCardIconClicked: () -> Unit = {},
        onTargetCardSelected: (SearchItemModel) -> Unit = {},
        onSourceCardSelected: (Card) -> Unit = {},
        dismissCardBottomSheet: () -> Unit = {},
        onToolbarIconClicked: () -> Unit = {},
        onAddCardIconClicked: () -> Unit = {},
        cardNumber: MutableState<String> = mutableStateOf(""),
        onDeleteItemClicked: (SearchItemModel) -> Unit,
        onClearCardClicked: () -> Unit = {},
    ) {

        if (state.showCardListBottomSheet) {
            CardsDestinationBottomSheet(
                dismiss = {
                    dismissCardBottomSheet()
                },
                itemList = state.destinationCardList,
                onCardSelected = {
                    onTargetCardSelected(it)
                },
                leftIcon = com.jabozaroid.abopay.feature.cardtocard.R.drawable.ic_delete_item,
                onLeftIconClicked = {
                    onDeleteItemClicked(it)
                })
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(AppTheme.colorScheme.aboBackgroundScreen)

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
                toolbarTitle = aboPayStringResource(R.string.card_to_card),
                onRightIconClicked = onToolbarIconClicked)

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
                    cardList = state.userCardList,
                    onAddCardIconClicked = onAddCardIconClicked,
                    onCardSelected = onSourceCardSelected
                )

                C2CInputValueContent(
                    state = state,
                    modifier = Modifier
                        .padding(horizontal = Dimens.size_8)
                        .weight(1f),
                    cardNumber = cardNumber,
                    onCardValueChanged = onCardValueChanged,
                    onPriceValueChanged = onPriceValueChanged,
                    onDescriptionValueChanged = onDescriptionValueChanged,
                    onCardIconClicked = onCardIconClicked,
                    onClearCardClicked = onClearCardClicked
                )


                ButtonBox(
                    state = state, modifier = Modifier
                        .wrapContentHeight()
                        .padding(
                            horizontal = Dimens.size_16
                        ),
                    onSaveSwitchStateChanged = onSaveSwitchStateChanged
                )


                AppPrimaryButton(
                    enabled = state.isEnableC2CButton(),
                    onClick = onConfirmButtonClicked,
                    text = aboPayStringResource(R.string.confirm),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            Dimens.size_12
                        )
                )

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
            MainContent(CardToCardUiModel(), onDeleteItemClicked = {})
        }
    }
}
