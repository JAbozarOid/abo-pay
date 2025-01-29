package com.jabozaroid.abopay.feature.cardmanagement.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.QuestionnaireBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.SwitchComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.AddUserNewCardBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementAction
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementEvent
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementUiModel
import com.jabozaroid.abopay.feature.cardmanagement.view.bottomsheet.AddDestinationCardBottomSheet
import com.jabozaroid.abopay.feature.cardmanagement.view.bottomsheet.EditCardBottomSheet
import com.jabozaroid.abopay.feature.cardmanagement.view.bottomsheet.EditDestinationCardBottomSheet
import com.jabozaroid.abopay.feature.cardmanagement.view.subcomponent.DestinationCardsContent
import com.jabozaroid.abopay.feature.cardmanagement.view.subcomponent.MyCardsContent
import com.jabozaroid.abopay.feature.cardmanagement.viewmodel.CardManagementViewModel

/**
 * Created on 24,November,2024
 */

class CardManagementScreen :
    BaseScreen<CardManagementUiModel, CardManagementAction, CardManagementEvent>(
        route = ApplicationRoutes.cardManagementScreenRoute,
        name = "CardManagementScreen"
    ) {

    @Composable
    override fun ViewModel(): CardManagementViewModel = hiltViewModel()

    @Composable
    override fun Content(state: CardManagementUiModel) {
        val viewModel = ViewModel()

        CardManagementMainScreen(
            state = state,
            onNavigateBack = {
                viewModel.process(action = CardManagementAction.NavigateUp)
            },
            onDestinationDeleteIconClicked = {
                viewModel.process(CardManagementAction.OnShowDeleteDestinationCardBottomSheet(it))
            },
            onDestinationEditIconClicked = {
                viewModel.process(CardManagementAction.OnShowEditDestinationCardBottomSheet(it))
            },
            onCopyCardActionSelect = { clipboardManager, cardNumber ->
                viewModel.process(
                    CardManagementAction.OnCopyCardClicked(
                        clipboardManager = clipboardManager,
                        cardNumber = cardNumber
                    )
                )
                Log.d("MAMAD", "Content: $cardNumber")
            },
            onUserCardSelected = {
                viewModel.process(CardManagementAction.OnUserCardSelected(it))
            },
            addNewSourceCard = {
                viewModel.process(CardManagementAction.OnShowAddCardBottomSheet)
            },
            onDefaultCardSelected = {
                viewModel.process(CardManagementAction.OnShowDefaultCardBottomSheet)
            },
            onDeleteCardSelected = {
                viewModel.process(CardManagementAction.OnShowDeleteCardBottomSheet)
            },
            onEditCardSelected = {
                viewModel.process(CardManagementAction.OnShowEditCardBottomSheet)
            },
            addNewDestinationCard = {
                viewModel.process(CardManagementAction.OnShowAddDestinationCardBottomSheet)
            }
        )
        if (state.addCardBottomSheetUiModel.isBottomSheetVisible) {
            AddUserNewCardBottomSheet(
                cardInformationUiModel = state.addCardBottomSheetUiModel.cardInformationUiModel,
                bottomSheetTitle = aboPayStringResource(id = R.string.add_card),
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideAddCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideAddCardBottomSheet)
                    viewModel.process(CardManagementAction.OnUserCardAdded(state.addCardBottomSheetUiModel.cardInformationUiModel.card))
                },
                confirmBtnText = aboPayStringResource(id = R.string.add),
                onUserCardNumberChanged = {
                    viewModel.process(CardManagementAction.OnUserCardNumberChanged(it))
                },
                onCardMonthChanged = {
                    viewModel.process(CardManagementAction.OnUserCardMonthChanged(it))
                },
                onCardYearChanged = {
                    viewModel.process(CardManagementAction.OnUserCardYearChanged(it))
                },
                onCardOwnerNameChanged = {
                    viewModel.process(CardManagementAction.OnUserCardOwnerNameChanged(it))
                },
                onDefaultCardStateChange = {
                    viewModel.process(
                        CardManagementAction.OnDefaultCardStateChanged(
                            it
                        )
                    )
                },
                scanCardVisibility = state.scanCardVisibility
            )
        }
        if (state.defaultCardBottomSheetUiModel.isBottomSheetVisible) {
            QuestionnaireBottomSheet(
                bottomSheetTitle = aboPayStringResource(id = R.string.default_title),
                bottomSheetQuestion = aboPayStringResource(id = R.string.default_card_question),
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideDefaultCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideDefaultCardBottomSheet)
                    viewModel.process(CardManagementAction.OnDefaultCardSelectedBottomSheet)
                },
            )
        }
        if (state.deleteCardBottomSheetUiModel.isBottomSheetVisible) {
            QuestionnaireBottomSheet(
                bottomSheetTitle = aboPayStringResource(id = R.string.delete_card),
                bottomSheetQuestion = aboPayStringResource(id = R.string.delete_card_question),
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideDeleteCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideDeleteCardBottomSheet)
                    viewModel.process(CardManagementAction.OnDeleteCardSelectedBottomSheet)
                },
            )
        }

        if (state.editCardBottomSheetUiModel.isBottomSheetVisible) {
            EditCardBottomSheet(
                editCardBottomSheetUiModel = state.editCardBottomSheetUiModel,
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideEditCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideEditCardBottomSheet)
                    viewModel.process(CardManagementAction.OnEditCardSelectedBottomSheet)
                },
                onCardNameChanged = {
                    viewModel.process(CardManagementAction.OnEditCardNameChanged(it))
                },
                onMonthChanged = {
                    viewModel.process(CardManagementAction.OnEditCardMonthChanged(it))
                },
                onYearChanged = {
                    viewModel.process(CardManagementAction.OnEditCardYearChanged(it))
                }
            )
        }
        if (state.editDestinationCardBottomSheetUiModel.isBottomSheetVisible) {
            EditDestinationCardBottomSheet(
                card = state.editDestinationCardBottomSheetUiModel.card,
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideEditDestinationCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideEditDestinationCardBottomSheet)
                    viewModel.process(CardManagementAction.OnDestinationCardEditClicked(it))
                },
            )
        }
        if (state.addDestinationCardBottomSheetUiModel.isBottomSheetVisible) {
            AddDestinationCardBottomSheet(
                addDestinationCardBottomSheetUiModel = state.addDestinationCardBottomSheetUiModel,
                onCardNumberChanged = {
                    viewModel.process(CardManagementAction.OnAddDestinationCardNumberChanged(it))
                },
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideAddDestinationCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideAddDestinationCardBottomSheet)
                    viewModel.process(CardManagementAction.OnAddDestinationCardClicked)
                },
            )
        }

        if (state.deleteDestinationCardBottomSheetUiModel.isBottomSheetVisible) {
            QuestionnaireBottomSheet(
                bottomSheetTitle = aboPayStringResource(id = R.string.delete_card),
                bottomSheetQuestion = aboPayStringResource(id = R.string.delete_card_question),
                onHideBottomSheet = { viewModel.process(CardManagementAction.OnHideDeleteDestinationCardBottomSheet) },
                onBtnConfirmClicked = {
                    viewModel.process(CardManagementAction.OnHideDeleteDestinationCardBottomSheet)
                    viewModel.process(CardManagementAction.OnDestinationCardDeleteClicked)
                },
            )
        }
    }

}

@Composable
fun CardManagementMainScreen(
    state: CardManagementUiModel,
    onNavigateBack: () -> Unit = {},
    onDestinationDeleteIconClicked: (SearchItemModel) -> Unit = {},
    onUserCardSelected: (Card) -> Unit = {},
    onCopyCardActionSelect: (ClipboardManager, String) -> Unit = { _, _ -> },
    onDestinationEditIconClicked: (SearchItemModel) -> Unit = {},
    addNewSourceCard: () -> Unit = {},
    onDefaultCardSelected: () -> Unit = {},
    onDeleteCardSelected: () -> Unit = {},
    onEditCardSelected: () -> Unit = {},
    addNewDestinationCard: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.aboBackgroundScreen)
    ) {
        AppToolbar(
            modifier = Modifier
                .padding(top = Dimens.size_8)
                .fillMaxWidth(),
            toolbarTitle = aboPayStringResource(id = R.string.card_management_title),
            onRightIconClicked = onNavigateBack
        )
        CardManagementContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.size_4,
                    start = Dimens.size_8,
                    end = Dimens.size_8,
                    bottom = Dimens.size_8
                )
                .clip(RoundedCornerShape(Dimens.size_12))
                .background(AppTheme.colorScheme.background),
            state = state,
            onDestinationDeleteIconClicked = onDestinationDeleteIconClicked,
            onDestinationEditIconClicked = onDestinationEditIconClicked,
            onCopyCardButtonClicked = onCopyCardActionSelect,
            onUserCardSelected = onUserCardSelected,
            addNewSourceCard = addNewSourceCard,
            onDefaultCardSelected = onDefaultCardSelected,
            onDeleteCardSelected = onDeleteCardSelected,
            onEditCardSelected = onEditCardSelected,
            addNewDestinationCard = addNewDestinationCard
        )
    }
}

@Composable
fun Actionbar(
    addNewSourceCard: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(
                vertical = Dimens.size_12,
                horizontal = Dimens.size_20
            )
            .background(AppTheme.colorScheme.background)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AppOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = {

            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.jabozaroid.abopay.feature.cardmanagement.R.drawable.ic_card_to_card),
                    contentDescription = null
                )
            }, text = {
                Text(
                    aboPayStringResource(id = R.string.card_to_card),
                    style = AppTheme.typography.text_12PX_16SP_M
                )
            }
        )
        Spacer(modifier = Modifier.width(Dimens.size_8))
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = {
                addNewSourceCard()
            },
            text = {
                Text(
                    aboPayStringResource(id = R.string.add_card),
                    style = AppTheme.typography.text_12PX_16SP_M
                )
            }

        )
    }
}

@Composable
fun CardManagementContent(
    modifier: Modifier = Modifier,
    state: CardManagementUiModel,
    onUserCardSelected: (Card) -> Unit,
    onDestinationDeleteIconClicked: (SearchItemModel) -> Unit,
    onDestinationEditIconClicked: (SearchItemModel) -> Unit,
    onCopyCardButtonClicked: (ClipboardManager, String) -> Unit = { _, _ -> },
    addNewSourceCard: () -> Unit = {},
    onDefaultCardSelected: () -> Unit = {},
    onDeleteCardSelected: () -> Unit = {},
    onEditCardSelected: () -> Unit = {},
    addNewDestinationCard: () -> Unit = {},
) {
    Column(modifier = modifier) {
        var selectedIndex by remember { mutableIntStateOf(1) }
        SwitchComponent(
            modifier = Modifier.padding(
                start = Dimens.size_12,
                end = Dimens.size_12,
                top = Dimens.size_16,
                bottom = Dimens.size_5
            ),
            initialSelectedIndex = 1,
            titles = listOf(
                aboPayStringResource(id = R.string.destination_cards),
                aboPayStringResource(id = R.string.my_cards)
            ),
            onTitleSelected = { index ->
                selectedIndex = index
            },
        )
        when (selectedIndex) {
            0 -> {
                DestinationCardsContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    state = state,
                    onDeleteIconClicked = onDestinationDeleteIconClicked,
                    onEditIconClicked = onDestinationEditIconClicked,
                )
                Actionbar {
                    addNewDestinationCard()
                }
            }

            1 -> {
                CardManagementMyCardsContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f), state = state,
                    onCardSelected = onUserCardSelected,
                    onCopyCardButtonClicked = onCopyCardButtonClicked,
                    onDefaultCardSelected = onDefaultCardSelected,
                    onDeleteCardSelected = onDeleteCardSelected,
                    onEditCardSelected = onEditCardSelected
                )
                Actionbar {
                    addNewSourceCard()
                }
            }
        }
        
    }
}


@Composable
fun CardManagementMyCardsContent(
    modifier: Modifier = Modifier,
    state: CardManagementUiModel,
    onCardSelected: (Card) -> Unit,
    onCopyCardButtonClicked: (ClipboardManager, String) -> Unit = { _, _ -> },
    onDefaultCardSelected: () -> Unit = {},
    onDeleteCardSelected: () -> Unit = {},
    onEditCardSelected: () -> Unit = {},
) {
    if (state.userCardList.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                aboPayStringResource(id = R.string.no_my_cards),
                style = AppTheme.typography.text_12PX_16SP_M,
                color = AppTheme.colorScheme.aboTitleText3,
                textAlign = TextAlign.Center
            )
        }
    } else {
        MyCardsContent(
            modifier = modifier,
            state = state,
            onActionCopy = onCopyCardButtonClicked,
            onCardSelected = onCardSelected,
            onDefaultCardSelected = onDefaultCardSelected,
            onDeleteCardSelected = onDeleteCardSelected,
            onEditCardSelected = onEditCardSelected,
        )
    }
}

@Preview(showBackground = true)
@ThemePreviews
@DevicePreviews
@Composable
internal fun PreviewCardManagementContent() {

    AppTheme {
        CardManagementMainScreen(
            state = CardManagementUiModel()
        )

    }
}