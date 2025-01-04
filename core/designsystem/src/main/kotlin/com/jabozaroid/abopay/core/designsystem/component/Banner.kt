package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.BannerUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalBannerList(
    modifier: Modifier,
    banners: List<BannerUiModel>,
    onBannerClicked: ((banner: BannerUiModel) -> Unit)?
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { banners.size })

    HorizontalPager(
        reverseLayout = true,
        modifier = modifier
            .padding(horizontal = Dimens.size_15),
        state = pagerState,
        pageSpacing = Dimens.size_8,
    ) {

        Banner(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.size_80),
            image = banners[it].img,
            onBannerClicked = {
                onBannerClicked?.invoke(banners[it])
            }
        )

    }

}

@Composable
private fun Banner(modifier: Modifier, image: Int, onBannerClicked: () -> Unit?) {

    Image(
        contentScale = ContentScale.FillBounds,
        modifier = modifier,
        painter = painterResource(id = image),
        contentDescription = "banner"
    )

}

@Preview
@Composable
fun PreviewHorizontalBannerList() {

    val banners = listOf(
        BannerUiModel(
            "1",
            R.drawable.banner3
        ),
        BannerUiModel(
            "2",
            R.drawable.banner2
        ),
        BannerUiModel(
            "3",
            R.drawable.banner1
        ),
    )

    AppTheme {

        AppBackground(modifier = Modifier) {

        }

        HorizontalBannerList(
            modifier = Modifier.fillMaxWidth(),
            banners = banners,
            onBannerClicked = {}
        )

    }

}