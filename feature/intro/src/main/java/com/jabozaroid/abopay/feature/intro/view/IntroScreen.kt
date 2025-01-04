package com.jabozaroid.abopay.feature.intro.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.intro.model.IntroAction
import com.jabozaroid.abopay.feature.intro.model.IntroEvent
import com.jabozaroid.abopay.feature.intro.model.IntroItemModel
import com.jabozaroid.abopay.feature.intro.model.IntroUiModel
import com.jabozaroid.abopay.feature.intro.preview.introItem
import com.jabozaroid.abopay.feature.intro.viewmodel.IntroViewModel


class IntroScreen : BaseScreen<IntroUiModel, IntroAction, IntroEvent>(
    name = "intro",
    route = ApplicationRoutes.introScreenRoute
) {
    @Composable
    override fun ViewModel(): IntroViewModel = hiltViewModel()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content(state: IntroUiModel) {

        val viewModel = ViewModel()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (btnEnter, pager, indicator) = createRefs()

            val pagerState =
                rememberPagerState(initialPage = 0, pageCount = { state.introItems.size })

            HorizontalPager(
                modifier = Modifier
                    .constrainAs(pager) {
                        top.linkTo(parent.top, Dimens.size_34)
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                        bottom.linkTo(indicator.top, Dimens.size_34)
                        height = Dimension.fillToConstraints
                    }
                    .fillMaxSize(),
                state = pagerState
            ) {

                IntroItemView(
                    modifier = Modifier,
                    item = state.introItems[it]
                )

            }

            IntroIndicators(
                modifier = Modifier
                    .constrainAs(indicator) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(btnEnter.top, Dimens.size_8)
                    },
                itemCount = pagerState.pageCount,
                selectedIndex = pagerState.settledPage
            )

            AppPrimaryButton(
                modifier = Modifier
                    .constrainAs(btnEnter) {
                        bottom.linkTo(parent.bottom, Dimens.size_8)
                        start.linkTo(parent.start, Dimens.size_8)
                        end.linkTo(parent.end, Dimens.size_8)
                        width = Dimension.fillToConstraints
                    },
                onClick = {
                    viewModel.process(IntroAction.EntryButtonClicked)
                },
                text = aboPayStringResource(id = R.string.enter_title)
            )


        }

    }

    @Composable
    private fun IntroIndicators(modifier: Modifier, itemCount: Int, selectedIndex: Int) {

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(itemCount) {
                Box(
                    modifier = Modifier
                        .size(Dimens.size_10, Dimens.size_10)
                        .border(
                            width = Dimens.size_1,
                            shape = CircleShape,
                            color = AppTheme.colorScheme.primary
                        )
                        .background(
                            if (selectedIndex == it) AppTheme.colorScheme.primary else AppTheme.colorScheme.background,
                            CircleShape
                        )
                ) {

                }
                Spacer(modifier = Modifier.width(Dimens.size_2))
            }
        }

    }

    @Composable
    private fun IntroItemView(
        modifier: Modifier,
        item: IntroItemModel
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(Dimens.size_15),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp),
                painter = painterResource(id = item.image),
                contentDescription = item.image.toString()
            )

            Spacer(modifier = Modifier.height(Dimens.size_80))

            ProvideTextStyle(
                value = AppTheme.typography.text_14PX_19SP_B.copy(
                    fontWeight = FontWeight.Bold
                )
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = item.title
                )
            }

            Spacer(modifier = Modifier.height(Dimens.size_20))

            ProvideTextStyle(
                value = AppTheme.typography.text_14PX_19SP_B
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = item.description
                )
            }

        }

    }

    @ThemePreviews
    @Composable
    private fun PreviewIntroItemView() {

        AppTheme {

            AppBackground(modifier = Modifier) {

            }

            IntroItemView(
                modifier = Modifier,
                item = introItem
            )

        }

    }

}
