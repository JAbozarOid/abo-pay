package com.jabozaroid.abopay.feature.finndow.view.shadowing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeAction
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeEvent
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeUiModel
import com.jabozaroid.abopay.feature.finndow.model.home.ShadowingCoursesName
import com.jabozaroid.abopay.feature.finndow.viewmodel.FinndowHomeViewModel

class FinndowShadowingCoursesScreen :
    BaseScreen<FinndowHomeUiModel, FinndowHomeAction, FinndowHomeEvent>(
        route = ApplicationRoutes.FINNDOW_SHADOWING_COURSES_SCREEN_ROUTE,
        name = "FinndowShadowingCoursesScreen"
    ) {
    @Composable
    override fun ViewModel(): FinndowHomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: FinndowHomeUiModel) {
        val viewModel = ViewModel()
        LaunchedEffect(1) {
            viewModel.process(FinndowHomeAction.OnRequestShadowingCourses)
        }
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
            MainContent(
                state,
                onNavigateBack = {
                    viewModel.process(action = FinndowHomeAction.NavigateUp)
                },
                onCourseItemClicked = {
                    viewModel.process(action = FinndowHomeAction.OnRequestShadowingCoursesDetail(it))
                }
            )
        }
    }

    @Composable
    private fun MainContent(
        state: FinndowHomeUiModel,
        onNavigateBack: () -> Unit = {},
        onCourseItemClicked: (String) -> Unit = {}
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundScreen)
        ) {

            val (toolbarRef, content) = createRefs()
            AppToolbar(
                textStyle = TextStyle(
                    fontFamily = AppTheme.typography.text_48PX_24SP_B.fontFamily,
                    fontSize = AppTheme.typography.text_48PX_24SP_B.fontSize
                ),
                modifier = Modifier
                    .constrainAs(toolbarRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = Dimens.size_8)
                    .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(id = R.string.shadowing_courses),
                onRightIconClicked = onNavigateBack,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.size_4,
                        start = Dimens.size_8,
                        end = Dimens.size_8,
                        bottom = Dimens.size_25
                    )
                    .clip(RoundedCornerShape(Dimens.size_12))
                    .background(AppTheme.colorScheme.background)
                    .constrainAs(
                        content
                    ) {
                        top.linkTo(toolbarRef.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 2),
                    contentPadding = PaddingValues(
                        vertical = Dimens.size_8,
                        horizontal = Dimens.size_16
                    ),

                    modifier = Modifier
                        .background(AppTheme.colorScheme.background)
                        .fillMaxWidth()
                ) {

                    items(
                        items = state.shadowingCoursesUiModel.shadowingCourses,
                        key = { item -> item.courseIndex }
                    ) { item ->
                        IconItem(
                            modifier = Modifier,
                            onClick = {
                                onCourseItemClicked(it)
                            },
                            item = item,

                        )
                    }

                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.size_16),
                    thickness = Dimens.size_1,
                    color = AppTheme.colorScheme.aboLine,
                )
            }
        }
    }

    @Composable
    fun IconItem(
        modifier: Modifier,
        onClick: (String) -> Unit,
        item: ShadowingCoursesName,
    ) {
        AppButton(
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor =item.courseColor,
                contentColor = AppTheme.colorScheme.aboWhiteBackground
            ),
            onClick = {
                onClick(item.courseName)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.size_12)
        ) {
            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                Text(text = item.courseName)
            }
        }
    }

    @Preview(showBackground = true)
    @ThemePreviews
    @Composable
    fun PreviewFinndowHomeScreen() {
        AppTheme {
            MainContent(state = FinndowHomeUiModel(loading = true))
        }
    }
}

