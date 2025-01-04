package com.jabozaroid.abopay.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jabozaroid.abopay.core.designsystem.component.AppTextFieldItemsLabel
import com.jabozaroid.abopay.core.designsystem.component.AppTextFieldLabelWithIcon
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_0
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_10
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_16
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_3
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_34
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_40
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_8
import com.jabozaroid.abopay.core.domain.model.home.Category
import com.jabozaroid.abopay.core.domain.model.home.CategoryItem
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.home.R
import com.jabozaroid.abopay.feature.home.model.HomeAction
import com.jabozaroid.abopay.feature.home.model.HomeEvent
import com.jabozaroid.abopay.feature.home.model.HomeUiModel
import com.jabozaroid.abopay.feature.home.preview.previewHomeCategoryItem
import com.jabozaroid.abopay.feature.home.preview.previewHomeService
import com.jabozaroid.abopay.feature.home.view.bottomnavigation.HomeBottomNavigation
import com.jabozaroid.abopay.feature.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield


class HomeScreen : BaseScreen<HomeUiModel, HomeAction, HomeEvent>(
    name = "home",
    route = ApplicationRoutes.homeScreenRoute
) {

    @Composable
    override fun ViewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: HomeUiModel) {
        val viewModel = ViewModel()

        AboPayHomeScreen(state,
            onClickToolbarRightIcon = {
                viewModel.process(HomeAction.NavigateToKahroba)
            },
            onServiceItemClicked = {
                viewModel.handleNavigation(it.categoryId, it.title)
            })

    }

    @Composable
    private fun AboPayHomeScreen(
        state: HomeUiModel,
        onServiceItemClicked: (CategoryItem) -> Unit,
        onClickToolbarRightIcon: () -> Unit,
    ) {
        Scaffold(
            containerColor = AppTheme.colorScheme.ivaWhiteBackground,
            topBar = { HomeToolbar(onClickRightIcon = onClickToolbarRightIcon) },
            bottomBar = { HomeBottomNavigation() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        size_0,
                        padding.calculateTopPadding(),
                        size_0,
                        padding.calculateBottomPadding()
                    )
            ) {
                HomeBanner()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = size_10)
                ) {
                    state.homeServices?.let {
                        val list: MutableList<Category> = mutableListOf()
                        for (item in it.categories) {
                            if (item.id == 3) {
                                list.add(0, item)
                            } else {
                                list.add(item)
                            }
                        }
                        items(list) { item ->
                            HomeServices(item, onServiceItemClicked)
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(size_8)
                            )
                        }
                    }

                }

            }
        }
    }


    @Composable
    internal fun HomeToolbar(onClickRightIcon: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(size_8, size_34, size_8, size_10)
        ) {
            Image(
                modifier = Modifier
                    .padding(start = Dimens.size_16)
                    .align(Alignment.CenterStart)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onClickRightIcon() },
                painter = painterResource(id = R.drawable.ic_kahroba),
                contentDescription = null
            )
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.abo_pay_logo),
                contentDescription = null
            )
        }

    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    internal fun HomeBanner() {
        val pagerState = rememberPagerState(initialPage = 2)
        val imageSlider = listOf(
            painterResource(id = R.drawable.banner_sample),
            painterResource(id = R.drawable.banner_sample),
            painterResource(id = R.drawable.banner_sample)
        )
        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(4000)
                pagerState.animateScrollToPage(
                    page = if (pagerState.currentPage > 0) {
                        pagerState.currentPage - 1
                    } else {
                        pagerState.pageCount - 1
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(size_0, size_0, size_10, size_8)
        )
        {
            HorizontalPager(
                count = imageSlider.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                Image(
                    painter = imageSlider[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = size_10)
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(size_16),
                activeColor = AppTheme.colorScheme.ivaWhiteBackground
            )
        }
    }

    @Composable
    internal fun HomeServices(
        category: Category,
        onServiceItemClicked: (CategoryItem) -> Unit,
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(
                    android.graphics.Color.parseColor(
                        category.backgroundColor
                    )
                )
            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(size_8),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_edit_home),
                        contentDescription = null,
                        modifier = Modifier.alpha(if (category.id == 3) 1f else 0f)
                    )
                    AppTextFieldLabelWithIcon(
                        modifier = Modifier.padding(end = size_8),
                        value = category.title,
                        icon = if (category.id == 1) R.drawable.iva_plus_tag else -1,
                        subtitle = if (category.description.isNotEmpty() && category.description.isNotBlank())
                            "(${category.description})"
                        else ""
                    )
                }
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(size_10, size_0, size_0, size_16)
                    ) {
                        items(
                            category.categoryItems
                        ) {
                            ServiceItem(it) {
                                onServiceItemClicked(it)
                            }
                        }

                    }
                }
            }
        }
    }

    @Composable
    internal fun ServiceItem(
        categoryItem: CategoryItem,
        onServiceItemClicked: () -> Unit,
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val itemSize = screenWidth * 0.20
        Card(
            modifier = Modifier
                .padding(start = size_3, end = size_3)
                .size(itemSize.dp, itemSize.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onServiceItemClicked()
                },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = size_8)
                        .size(38.dp)
                        .align(Alignment.TopCenter),
                    painter = rememberAsyncImagePainter(model = categoryItem.iconName),
//                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )
                AppTextFieldItemsLabel(
                    modifier = Modifier
                        .padding(top = size_40)
                        .align(Alignment.Center),
                    value = categoryItem.title
                )
            }
        }
    }

    @ThemePreviews
    @Composable
    internal fun AboPayHomeScreenPreview() {
        AppTheme {
            AboPayHomeScreen(
                state = HomeUiModel(
                    homeServices = previewHomeService
                ),
                onClickToolbarRightIcon = {},
                onServiceItemClicked = {}
            )
        }
    }

    @ThemePreviews
    @Composable
    internal fun AboPayServiceItemPreview() {
        AppTheme {
            ServiceItem(
                categoryItem = previewHomeCategoryItem
            ) {

            }
        }
    }
}

