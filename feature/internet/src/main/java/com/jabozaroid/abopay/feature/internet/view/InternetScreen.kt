package com.jabozaroid.abopay.feature.internet.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.FrequentManagerComponent
import com.jabozaroid.abopay.core.designsystem.component.MobileContent
import com.jabozaroid.abopay.core.designsystem.component.QuestionnaireBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.SelectableGridComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.PhoneNumberItem
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.internet.model.InternetAction
import com.jabozaroid.abopay.feature.internet.model.InternetEvent
import com.jabozaroid.abopay.feature.internet.model.InternetUiModel
import com.jabozaroid.abopay.feature.internet.view.bottomsheet.TopUpInternetBottomSheet
import com.jabozaroid.abopay.feature.internet.viewmodel.InternetViewModel

class InternetScreen : BaseScreen<InternetUiModel, InternetAction, InternetEvent>(
    route = ApplicationRoutes.internetScreenRoute,
    name = "InternetHomeScreen"
) {

    @Composable
    override fun ViewModel(): InternetViewModel = hiltViewModel()

    @Composable
    override fun Content(state: InternetUiModel) {

        val viewModel = ViewModel()
        val phoneNumberStateValue: MutableState<String> = rememberSaveable { mutableStateOf("") }

        LaunchedEffect(1) {
            viewModel.process(action = InternetAction.GetTopUpInternets)
        }

        InternetHomeContent(
            state = state,
            phoneNumberStateValue = phoneNumberStateValue,
            onPhoneNumberValidate = { mobileValue ->
                viewModel.process(action = InternetAction.UserPhoneNumberValue(mobileValue))
            },
            onDeleteUserFavoritePhoneNumber = { mobileValue ->
                viewModel.process(action = InternetAction.DeleteUserPhoneNumber(mobileValue))
            },
            onPhoneNumberError = { mobileValue ->
                viewModel.process(
                    action = InternetAction.PhoneNumberErrorValue(
                        mobileValue
                    )
                )
            },
            onPhoneNumberErrorStatus = { mobileValue ->
                viewModel.process(
                    action = InternetAction.PhoneNumberErrorStatus(
                        mobileValue
                    )
                )
            },
            onClearOperatorStatusByTextEmpty = {
                viewModel.process(action = InternetAction.ClearOperatorStatusByPhoneEmpty(it))
            },

            onFindOperatorIndexByUserInput = { mobileValue ->
                viewModel.process(
                    action = InternetAction.FindOperatorByUserInput(
                        mobileValue,
                        state.operatorUiModels
                    )
                )
            },

            onShowTopUpInternetsBottomSheet = { mobileValue ->
                state.operatorIndex?.let {
                    viewModel.process(
                        action = InternetAction.TopUpInternets(
                            state.operatorIndex,
                            state.operatorUiModels
                        )
                    )
                }
            },

            onPhoneNumberValueChange = { mobileValue ->
                viewModel.process(action = InternetAction.GetLatestPhoneNumberValue(mobileValue))
            },

            onNavigateBack = {
                viewModel.process(action = InternetAction.NavigateUp)
            },

            onSelectOperatorItem = { index ->
                viewModel.process(action = InternetAction.SelectOperatorItem(index))
            },

            onFrequentRemoveIconClicked = { item ->
                viewModel.process(action = InternetAction.FrequentRemoveIconClicked(item))
            }
        )


        if (state.topUpInternetBottomSheetUiModel.isBottomSheetVisible) {
            TopUpInternetBottomSheet(
                bottomSheetTitle = aboPayStringResource(id = R.string.select_sim_type),
                onHideBottomSheet = {
                    viewModel.process(action = InternetAction.HideTopUpInternetsBottomSheet)
                },
                onBtnConfirmClicked = { internetType ->
                    viewModel.process(
                        action = InternetAction.NavigateToInternetListScreen(
                            internetType
                        )
                    )
                },
                onBtnCancelClicked = { viewModel.process(action = InternetAction.HideTopUpInternetsBottomSheet) },
                confirmBtnText = aboPayStringResource(id = R.string.confirm),
                cancelBtnText = "",
                dualActionBtn = false
            )
        }

        if (state.removeBottomSheetUiModel.isRemoveBottomSheetVisible) {
            QuestionnaireBottomSheet(
                bottomSheetTitle = aboPayStringResource(id = R.string.delete_package),
                bottomSheetQuestion = aboPayStringResource(id = R.string.delete_package_confirm),
                onHideBottomSheet = {
                    viewModel.process(action = InternetAction.HideRemoveBottomSheet)
                },
                onBtnConfirmClicked = {
                    viewModel.process(
                        action = InternetAction.RemoveFrequentItem(
                            state.removeBottomSheetUiModel.selectedFrequentItem,
                        )
                    )
                }
            )
        }
    }
}

//region Internet home content
@Composable
private fun InternetHomeContent(
    state: InternetUiModel,
    phoneNumberStateValue: MutableState<String> = mutableStateOf(""),
    onPhoneNumberValidate: (value: String) -> Unit = {},
    onDeleteUserFavoritePhoneNumber: (value: String) -> Unit = {},
    onPhoneNumberError: (value: String) -> Unit = {},
    onPhoneNumberErrorStatus: (value: String) -> Unit = {},
    onClearOperatorStatusByTextEmpty: (value: Int?) -> Unit = {},
    onFindOperatorIndexByUserInput: (value: String) -> Unit = {},
    onShowTopUpInternetsBottomSheet: (value: String) -> Unit = {},
    onPhoneNumberValueChange: (value: String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onSelectOperatorItem: (Int?) -> Unit = {},
    onFrequentRemoveIconClicked: (FrequentUiModel) -> Unit,
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.aboBackgroundScreen)
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
            toolbarTitle = aboPayStringResource(id = R.string.internet_main_title),
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
                userPhoneNumbers = state.userInternetPhoneNumbers.map {
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

            // frequent content
            FrequentManagerComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(Dimens.size_8),
                frequentItems = state.frequentItems,
                onFrequentRemoveIconClicked = onFrequentRemoveIconClicked,
                onFrequentEditIconClicked = {},
                title = aboPayStringResource(id = R.string.frequent_package),
                description = aboPayStringResource(id = R.string.no_package_alert)
            )

            AppButton(
                enabled = state.isContinueEnable(),
                onClick = {
                    onShowTopUpInternetsBottomSheet(state.getMobileNumberValue())
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
    Text(
        color = AppTheme.colorScheme.aboOutlineButtonText,
        modifier = Modifier.padding(Dimens.size_4),
        style = AppTheme.typography.text_9PX_12SP_B.copy(
            textDirection = TextDirection.Rtl
        ),
        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.select_operator_if_tarabord)
    )
}
//endregion

//region divider section
@Composable
internal fun DividerSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.size_16,
                end = Dimens.size_16,
            )
            .height(Dimens.size_1)
            .background(AppTheme.colorScheme.aboBackgroundScreen)
    )
}
//endregion


@Preview(showBackground = true)
@ThemePreviews
@Composable
//@DevicePreviews
internal fun PreviewInternetHomeContent() {

    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        Column {
            InternetHomeContent(InternetUiModel(), onFrequentRemoveIconClicked = {})
        }

    }
}



