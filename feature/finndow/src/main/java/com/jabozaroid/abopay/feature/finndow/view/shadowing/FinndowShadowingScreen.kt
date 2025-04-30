package com.jabozaroid.abopay.feature.finndow.view.shadowing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.zIndex
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
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthAction
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeAction
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeEvent
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeUiModel
import com.jabozaroid.abopay.feature.finndow.view.shadowing.components.AudioPlayer
import com.jabozaroid.abopay.feature.finndow.view.shadowing.components.RecordButton
import com.jabozaroid.abopay.feature.finndow.viewmodel.FinndowAuthViewModel
import com.jabozaroid.abopay.feature.finndow.viewmodel.FinndowHomeViewModel

class FinndowShadowingScreen : BaseScreen<FinndowHomeUiModel, FinndowHomeAction, FinndowHomeEvent>(
    route = ApplicationRoutes.FINNDOW_SHADOWING_SCREEN_ROUTE, name = "FinndowShadowingScreen"
) {
    @Composable
    override fun ViewModel(): FinndowHomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: FinndowHomeUiModel) {
        val viewModel = ViewModel()
        LaunchedEffect(1) {
            viewModel.process(FinndowHomeAction.OnRequestShadowingPractice)
        }
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
            MainContent(
                state, onNavigateBack = {
                    viewModel.process(action = FinndowHomeAction.NavigateUp)
                },
                onNextPracticeBtnClicked = {
                    viewModel.process(FinndowHomeAction.OnNextPracticeBtnClicked)
                }
            )
        }
    }

    @Composable
    private fun MainContent(
        state: FinndowHomeUiModel,
        onNavigateBack: () -> Unit = {},
        onNextPracticeBtnClicked: () -> Unit = {}
    ) {
        val scrollState = rememberScrollState()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundScreen)
        ) {

            val (toolbarRef, content, bottomActions) = createRefs()
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
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
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
                        bottom.linkTo(bottomActions.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }) {
                // type and level section
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = state.shadowingUiModel.level ?: "Not Available",
                        style = AppTheme.typography.text_14PX_19SP_M,
                        color = AppTheme.colorScheme.finndowGreen,
                        modifier = Modifier
                            .padding(start = Dimens.size_12, top = Dimens.size_12)
                    )
                    Text(
                        text = state.shadowingUiModel.type ?: "Not Available",
                        style = AppTheme.typography.text_14PX_19SP_M,
                        color = AppTheme.colorScheme.finndowGreen,
                        modifier = Modifier
                            .padding(start = Dimens.size_12, top = Dimens.size_12)
                    )
                }

                // Red label : finnish exercises
                Text(
                    text = aboPayStringResource(R.string.finnish_exercises),
                    style = AppTheme.typography.text_12PX_16SP_M,
                    color = AppTheme.colorScheme.finndowRedBlue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = Dimens.size_12, top = Dimens.size_12)
                )

                Spacer(modifier = Modifier.height(Dimens.size_16))

                // Dark rounded card with finish text
                Card(
                    shape = RoundedCornerShape(Dimens.size_16),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = Dimens.size_12),
                    elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(Dimens.size_16)
                    ) {
                        Text(
                            text = state.shadowingUiModel.finnishText?.trimIndent()
                                ?: "Not Available",
                            style = AppTheme.typography.text_14PX_19SP_M.copy(
                                textDirection = TextDirection.Ltr
                            ),
                            color = AppTheme.colorScheme.aboWhiteBackground,
                            textAlign = TextAlign.Start,

                            )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.size_16))

                // Red label : pronunciation guide
                Text(
                    text = aboPayStringResource(R.string.pronunciation_guide),
                    style = AppTheme.typography.text_12PX_16SP_M,
                    color = AppTheme.colorScheme.finndowRedBlue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = Dimens.size_12)
                )

                // Dark rounded card with pronunciation guide
                Card(
                    shape = RoundedCornerShape(Dimens.size_16),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = Dimens.size_12),
                    elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(Dimens.size_16)
                    ) {
                        Text(
                            text = state.shadowingUiModel.pronunciationGuide?.trimIndent()
                                ?: "Not Available",
                            style = AppTheme.typography.text_14PX_19SP_M.copy(
                                textDirection = TextDirection.Ltr
                            ),
                            color = AppTheme.colorScheme.aboWhiteBackground,
                            textAlign = TextAlign.Start,

                            )
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.size_16))
                // Red label : translated in english
                Text(
                    text = aboPayStringResource(R.string.translate_in_english),
                    style = AppTheme.typography.text_12PX_16SP_M,
                    color = AppTheme.colorScheme.finndowRedBlue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = Dimens.size_12)
                )

                // Dark rounded card with english translated text
                Card(
                    shape = RoundedCornerShape(Dimens.size_16),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = Dimens.size_12, vertical = Dimens.size_12),
                    elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(Dimens.size_16)
                    ) {
                        Text(
                            text = state.shadowingUiModel.englishTranslation?.trimIndent()
                                ?: "Not Available",
                            style = AppTheme.typography.text_14PX_19SP_M.copy(
                                textDirection = TextDirection.Ltr
                            ),
                            color = AppTheme.colorScheme.aboWhiteBackground,
                            textAlign = TextAlign.Start,

                            )
                    }
                }
                AppButton(
                    enabled = true,
                    onClick = {
                        onNextPracticeBtnClicked()
                    },
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth()
                        .padding(Dimens.size_12)
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(aboPayStringResource(id = R.string.next_practice_btn))
                    }
                }

            }

            // Fixed Bottom Actions (AudioPlayer + RecordButton)
            Column(
                modifier = Modifier
                    .constrainAs(bottomActions) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(
                        top = Dimens.size_4,
                        start = Dimens.size_8,
                        end = Dimens.size_8,
                        bottom = Dimens.size_25
                    )
                    .clip(RoundedCornerShape(Dimens.size_12))
                    .background(AppTheme.colorScheme.background)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(Dimens.size_8))
                AudioPlayer()
                Spacer(modifier = Modifier.height(Dimens.size_8))
                RecordButton()
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

