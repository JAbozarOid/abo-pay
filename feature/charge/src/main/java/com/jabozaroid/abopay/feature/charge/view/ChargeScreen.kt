package com.jabozaroid.abopay.feature.charge.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.FrequentManagerComponent
import com.jabozaroid.abopay.core.designsystem.component.MobileContent
import com.jabozaroid.abopay.core.designsystem.component.QuestionnaireBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.SelectableGridComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.PhoneNumberItem
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.charge.model.ChargeAction
import com.jabozaroid.abopay.feature.charge.model.ChargeEvent
import com.jabozaroid.abopay.feature.charge.model.ChargeUiModel
import com.jabozaroid.abopay.feature.charge.view.bottomsheet.TopUpChargesBottomSheet
import com.jabozaroid.abopay.feature.charge.viewmodel.ChargeViewModel

class ChargeScreen : BaseScreen<ChargeUiModel, ChargeAction, ChargeEvent>(
    route = ApplicationRoutes.chargeScreenRoute,
    name = "ChargeHomeScreen"
) {

    @Composable
    override fun ViewModel(): ChargeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: ChargeUiModel) {

        val viewModel = ViewModel()
        val phoneNumberStateValue: MutableState<String> = rememberSaveable { mutableStateOf("") }

        LaunchedEffect(1) {
            viewModel.process(action = ChargeAction.GetTopUpCharges)
        }

        ChargeHomeContent(
            state = state,
            phoneNumberStateValue = phoneNumberStateValue,
            onPhoneNumberValidate = { mobileValue ->
                viewModel.process(action = ChargeAction.UserPhoneNumberValue(mobileValue))
            },
            onDeleteUserFavoritePhoneNumber = { mobileValue ->
                viewModel.process(action = ChargeAction.DeleteUserPhoneNumber(mobileValue))
            },
            onPhoneNumberError = { mobileValue ->
                viewModel.process(
                    action = ChargeAction.PhoneNumberErrorValue(
                        mobileValue
                    )
                )
            },
            onPhoneNumberErrorStatus = { mobileValue ->
                viewModel.process(
                    action = ChargeAction.PhoneNumberErrorStatus(
                        mobileValue
                    )
                )
            },
            onClearOperatorStatusByTextEmpty = {
                viewModel.process(action = ChargeAction.ClearOperatorStatusByPhoneEmpty(it))
            },

            onFindOperatorIndexByUserInput = { mobileValue ->
                viewModel.process(
                    action = ChargeAction.FindOperatorByUserInput(
                        mobileValue,
                        state.operatorUiModels
                    )
                )
            },

            onShowTopUpChargesBottomSheet = { mobileValue ->
                state.operatorIndex?.let {
                    viewModel.process(
                        action = ChargeAction.TopUpCharges(
                            state.operatorIndex,
                            state.operatorUiModels
                        )
                    )
                }
            },

            onPhoneNumberValueChange = { mobileValue ->
                viewModel.process(action = ChargeAction.GetLatestPhoneNumberValue(mobileValue))
            },

            onNavigateBack = {
                viewModel.process(action = ChargeAction.NavigateUp)
            },

            onSelectOperatorItem = { index ->
                viewModel.process(action = ChargeAction.SelectOperatorItem(index))
            },

            onFrequentRemoveIconClicked = { item ->
                viewModel.process(action = ChargeAction.FrequentRemoveIconClicked(item))
            }
        )

        if (state.topUpChargesBottomSheetUiModel.isBottomSheetVisible) {
            TopUpChargesBottomSheet(
                state,
                bottomSheetTitle = aboPayStringResource(id = R.string.select_charge),
                onHideBottomSheet = {
                    viewModel.process(action = ChargeAction.HideTopUpChargesBottomSheet)
                },
                onBtnConfirmClicked = {
                    if (state.topUpChargesBottomSheetUiModel.isChargeAmountSelect)
                        viewModel.process(action = ChargeAction.NavigateToPaymentScreenSuccessfully)
                    else
                        viewModel.process(action = ChargeAction.NavigateToPaymentScreenFailing)

                },
                onBtnCancelClicked = { viewModel.process(action = ChargeAction.HideTopUpChargesBottomSheet) },
                confirmBtnText = aboPayStringResource(id = R.string.confirm),
                cancelBtnText = aboPayStringResource(id = R.string.cancel),
                onSelectChargeAmount = { amountUiModel ->
                    viewModel.process(action = ChargeAction.SelectChargeAmount(amountUiModel))
                }
            )
        }

        if (state.removeBottomSheetUiModel.isRemoveBottomSheetVisible) {
            QuestionnaireBottomSheet(
                bottomSheetTitle = aboPayStringResource(id = R.string.delete_charge),
                bottomSheetQuestion = aboPayStringResource(id = R.string.delete_charge_confirm),
                onHideBottomSheet = {
                    viewModel.process(action = ChargeAction.HideRemoveBottomSheet)
                },
                onBtnConfirmClicked = {
                    viewModel.process(
                        action = ChargeAction.RemoveFrequentItem(
                            state.removeBottomSheetUiModel.selectedFrequentItem,
                        )
                    )
                }
            )
        }
    }
}

//region Charge home content
@Composable
private fun ChargeHomeContent(
    state: ChargeUiModel,
    phoneNumberStateValue: MutableState<String> = mutableStateOf(""),
    onPhoneNumberValidate: (value: String) -> Unit = {},
    onDeleteUserFavoritePhoneNumber: (value: String) -> Unit = {},
    onPhoneNumberError: (value: String) -> Unit = {},
    onPhoneNumberErrorStatus: (value: String) -> Unit = {},
    onClearOperatorStatusByTextEmpty: (value: Int?) -> Unit = {},
    onFindOperatorIndexByUserInput: (value: String) -> Unit = {},
    onShowTopUpChargesBottomSheet: (value: String) -> Unit = {},
    onPhoneNumberValueChange: (value: String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onSelectOperatorItem: (Int?) -> Unit = {},
    onFrequentRemoveIconClicked: (FrequentUiModel) -> Unit,
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.ivaBackgroundScreen)
    ) {
        var isOperatorSelectedItem by remember {
            mutableStateOf(false)
        }
        val (toolbarRef, content) = createRefs()
        AppToolbar(
            modifier = Modifier
                .constrainAs(toolbarRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = Dimens.size_8)
                .fillMaxWidth(),
            toolbarTitle = aboPayStringResource(id = R.string.charge_main_title),
            onRightIconClicked = onNavigateBack
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.size_4,
                    start = Dimens.size_8,
                    end = Dimens.size_8,
                    bottom = Dimens.size_8
                )
                .clip(RoundedCornerShape(Dimens.size_12))
                .background(AppTheme.colorScheme.background)
                .constrainAs(
                    content
                ) {
                    top.linkTo(toolbarRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            MobileContent(
                mobileNumberUiModel = state.mobileNumberUiModel,
                userPhoneNumbers = state.userChargePhoneNumbers.map {
                    PhoneNumberItem(
                        phoneNumber = it.phoneNumber,
                        ownerPhoneNumber = it.ownerPhoneNumber,
                        icon = it.icon
                    )
                },
                phoneNumberStateValue = phoneNumberStateValue,
                onPhoneNumberValidate = onPhoneNumberValidate,
                onDeleteUserFavoritePhoneNumber = onDeleteUserFavoritePhoneNumber,
                onPhoneNumberError = onPhoneNumberError,
                onPhoneNumberErrorStatus = onPhoneNumberErrorStatus,
                onClearOperatorStatusByTextEmpty = onClearOperatorStatusByTextEmpty,
                onFindOperatorIndexByUserInput = onFindOperatorIndexByUserInput,
                onPhoneNumberValueChange = onPhoneNumberValueChange,
            )
            TarabordTextContent()

            //region operators -->
            state.operatorIndex?.let {
                SelectableGridComponent(
                    modifier = Modifier.fillMaxWidth(),
                    items = state.operatorUiModels,
                    onItemClicked = { model ->
                        isOperatorSelectedItem = false
                        onSelectOperatorItem(model.index)
                    },
                    isSelected = isOperatorSelectedItem,
                    selectedIndex = state.operatorIndex
                )
            } ?: run {
                SelectableGridComponent(
                    modifier = Modifier.fillMaxWidth(),
                    items = state.operatorUiModels,
                    onItemClicked = {
                        isOperatorSelectedItem = true
                    }, isSelected = isOperatorSelectedItem, selectedIndex = null
                )
            }
            //endregion

            //DividerSection()

            // frequent content --> btn continue is in this content
            FrequentManagerComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(Dimens.size_8),
                state.frequentItems,
                onFrequentRemoveIconClicked = onFrequentRemoveIconClicked,
                onFrequentEditIconClicked = {},
                title = aboPayStringResource(id = R.string.frequent_charge),
                description = aboPayStringResource(id = R.string.no_charge_alert)
            )

            AppButton(
                enabled = state.isContinueEnable(),
                onClick = {
                    onShowTopUpChargesBottomSheet(state.getMobileNumberValue())
                },
                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .fillMaxWidth()
                    .padding(Dimens.size_12)
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(aboPayStringResource(id = R.string.continue_title))
                }
            }
        }
    }
}
//endregion


//region tarabord text content
@Composable
internal fun TarabordTextContent() {
    ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
        Text(
            color = AppTheme.colorScheme.ivaOutlineButtonText,
            modifier = Modifier.padding(Dimens.size_4),
            style = AppTheme.typography.text_9PX_12SP_B.copy(
                textDirection = TextDirection.Rtl
            ),
            text = aboPayStringResource(id = R.string.select_operator_if_tarabord)
        )
    }
}
//endregion


@Preview(showBackground = true)
@ThemePreviews
@DevicePreviews
@Composable
//@DevicePreviews
internal fun PreviewChargeHomeContent() {

    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        Column {
            ChargeHomeContent(state = ChargeUiModel(),
                onFrequentRemoveIconClicked = {}
            )
        }

    }
}



