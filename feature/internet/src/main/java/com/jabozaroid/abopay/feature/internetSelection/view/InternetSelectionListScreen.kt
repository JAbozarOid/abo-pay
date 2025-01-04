package com.jabozaroid.abopay.feature.internetSelection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.SwitchComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.UnsafeImageApp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.internet.model.InternetCatalog
import com.jabozaroid.abopay.feature.internet.model.InternetDurationType
import com.jabozaroid.abopay.feature.internetSelection.model.InternetSelectionAction
import com.jabozaroid.abopay.feature.internetSelection.model.InternetSelectionEvent
import com.jabozaroid.abopay.feature.internetSelection.model.InternetSelectionUiModel
import com.jabozaroid.abopay.feature.internetSelection.viewmodel.InternetSelectionViewModel

class InternetSelectionListScreen :
    BaseScreen<InternetSelectionUiModel, InternetSelectionAction, InternetSelectionEvent>(
        route = ApplicationRoutes.InternetSelectionListRoute + ApplicationRoutes.internetParam,
        name = "InternetSelectionListScreen"
    ) {

    @Composable
    override fun ViewModel(): InternetSelectionViewModel = hiltViewModel()

    @Composable
    override fun Content(state: InternetSelectionUiModel) {

        val viewModel = ViewModel()

        val selectedOperator = parameters[NavigationParam.SELECTED_OPERATOR]
        val simType = parameters[NavigationParam.SIM_TYPE]
        val operatorLogo = parameters[NavigationParam.OPERATOR_LOGO]?.replace('~', '/')
        selectedOperator?.let {
            simType?.let {
                LaunchedEffect(1) {
                    viewModel.process(
                        action = InternetSelectionAction.GetInternetList(
                            selectedOperator,
                            simType
                        )
                    )
                }
            }
        }


        InternetMainContent(
            state = state,
            operatorLogo = operatorLogo ?: "",
            onNavigateBack = {
                viewModel.process(action = InternetSelectionAction.NavigateUp)
            },
            onInternetSelected = { internetCatalog ->
                viewModel.process(action = InternetSelectionAction.NavigateToPaymentScreenSuccessfully)
            }
        )

    }

    @Composable
    fun InternetMainContent(
        state: InternetSelectionUiModel,
        onNavigateBack: () -> Unit = {},
        operatorLogo: String = "",
        onInternetSelected: (InternetCatalog) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.ivaBackgroundScreen)
        ) {
            AppToolbar(
                modifier = Modifier
                    .padding(top = Dimens.size_8)
                    .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(id = R.string.internet_main_title),
                onRightIconClicked = onNavigateBack
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = Dimens.size_4,
                        start = Dimens.size_8,
                        end = Dimens.size_8,
                        bottom = Dimens.size_8
                    )
                    .clip(RoundedCornerShape(Dimens.size_12))
                    .background(AppTheme.colorScheme.background)
            ) {
                var categorySelected by remember {
                    mutableIntStateOf(2)
                }
                SwitchComponent(
                    modifier = Modifier.padding(
                        Dimens.size_8,
                    ),
                    initialSelectedIndex = categorySelected,
                    cardBackgroundColor = AppTheme.colorScheme.ivaSwitchUnselected,
                    itemSelectedColor = AppTheme.colorScheme.ivaSwitchSelected,
                    itemUnselectedColor = AppTheme.colorScheme.ivaSwitchUnselected,
                    titles = listOf(
                        aboPayStringResource(id = R.string.monthly),
                        aboPayStringResource(id = R.string.weekly),
                        aboPayStringResource(id = R.string.daily)
                    ),
                    onTitleSelected = { categorySelected = it },
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.size_16),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.size_16),
                        thickness = Dimens.size_1,
                        color = AppTheme.colorScheme.ivaLine,
                    )

                    UnsafeImageApp(
                        modifier = Modifier
                            .background(AppTheme.colorScheme.background)
                            .size(Dimens.size_50),
                        lightLogo = operatorLogo,
                        darkLogo = operatorLogo,
                        placeholder = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_feature,
                        contentDescription = "operatorLogo"
                    )
                }
                InternetGroup(
                    state,
                    internetDurationType = when (categorySelected) {
                        0 -> InternetDurationType.MONTHLY
                        1 -> InternetDurationType.WEEKLY
                        else -> InternetDurationType.DAILY
                    },
                    onInternetSelected = onInternetSelected
                )
            }
        }
    }

    @Composable
    fun InternetGroup(
        state: InternetSelectionUiModel,
        internetDurationType: InternetDurationType,
        onInternetSelected: (InternetCatalog) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = Dimens.size_8
                ),
            horizontalAlignment = Alignment.End
        ) {
            state.internetCatalogList[internetDurationType]?.forEach { internetCatalogList ->
                item {
                    Text(
                        modifier = Modifier.padding(
                            end = Dimens.size_4,
                            top = Dimens.size_16
                        ),
                        style = AppTheme.typography.text_14PX_19SP_M.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        text = internetCatalogList.title,
                        color = Color.Black
                    )

                    internetCatalogList.internetCatalogList.forEach { internetCatalog ->
                        InternetItem(
                            internetCatalog = internetCatalog,
                            onInternetSelected = { onInternetSelected(internetCatalog) }
                        )
                    }
                }
            }

        }
    }

    @Composable
    fun InternetItem(
        internetCatalog: InternetCatalog,
        onInternetSelected: () -> Unit,
    ) {
        Row(
            modifier = Modifier
                .padding(top = Dimens.size_10)
                .fillMaxWidth()
                .height(Dimens.size_55)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onInternetSelected()
                }
                .clip(RoundedCornerShape(Dimens.size_12))
                .background(AppTheme.colorScheme.ivaBackgroundButton2),

            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.33f)
                    .background(AppTheme.colorScheme.ivaSwitchSelected),
                contentAlignment = Alignment.Center,
                content =
                {
                    Text(
                        text = "${internetCatalog.amount} ${aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.rial)}",
                        maxLines = 2,
                        style = AppTheme.typography.text_12PX_16SP_M.copy(
                            fontWeight = FontWeight(700)
                        )
                    )
                }
            )

            Text(
                text = internetCatalog.name,
                modifier = Modifier
                    .padding(
                        start = Dimens.size_16,
                        end = Dimens.size_8
                    )
                    .fillMaxWidth(),
                maxLines = 2,
                style = AppTheme.typography.text_10PX_13SP_B

            )
        }
    }


    @Preview(showBackground = true)
    @ThemePreviews
    @Composable
    fun PreviewInternetHomeContent() {

        AppTheme {
            AppBackground(modifier = Modifier) {

            }
            Column {
                InternetMainContent(
                    InternetSelectionUiModel(),
                    onInternetSelected = { internetcatalog -> })
                InternetItem(
                    onInternetSelected = {},
                    internetCatalog = InternetCatalog(),
                )
            }

        }
    }


    @Preview(showBackground = true)
    @ThemePreviews
    @Composable
    fun PreviewInternetItem() {

        AppTheme {
            AppBackground(modifier = Modifier) {

            }
            Column {
                InternetItem(
                    onInternetSelected = {},
                    internetCatalog = InternetCatalog(),
                )
            }

        }
    }
}



