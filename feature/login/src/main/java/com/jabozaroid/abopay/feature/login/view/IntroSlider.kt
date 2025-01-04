package com.jabozaroid.abopay.feature.login.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.login.model.IntroItemModel
import com.jabozaroid.abopay.feature.login.model.LoginUiModel

/**
 *  Created on 9/17/2024 
 **/


@Composable
fun IntroSlider(modifier: Modifier, state: LoginUiModel, pagerState: PagerState) {

    HorizontalPager(
        modifier = modifier, state = pagerState
    ) {

        IntroItemView(
            modifier = Modifier, item = state.introItems[it]
        )
    }
}


@Composable
private fun IntroItemView(
    modifier: Modifier, item: IntroItemModel
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(Dimens.size_4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(Dimens.size_4))

        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = item.image),
            contentDescription = item.image.toString(),
            contentScale = ContentScale.FillHeight
        )


    }

}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    selectedPage: Int = 0,
    defaultRadius: Dp = Dimens.size_10,
    selectedLength: Dp = 30.dp,
    space: Dp = Dimens.size_4,
    animationDurationInMillis: Int = 300,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage
            PageIndicatorView(
                isSelected = isSelected,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}


@Composable
fun PageIndicatorView(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    defaultRadius: Dp,
    selectedLength: Dp,
    defaultColor: Color = AppTheme.colorScheme.ivaBackgroundButton2,
    selectedColor: Color = AppTheme.colorScheme.primary,
    animationDurationInMillis: Int,
) {

    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            defaultColor
        }, animationSpec = tween(
            durationMillis = animationDurationInMillis,
        ), label = ""
    )
    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedLength
        } else {
            defaultRadius
        }, animationSpec = tween(
            durationMillis = animationDurationInMillis,
        ), label = ""
    )

    Canvas(
        modifier = modifier.size(
                width = width,
                height = defaultRadius,
            ),
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx(),
            ),
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




