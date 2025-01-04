package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun FrequentComponent(
    frequentUiModel: FrequentUiModel,
    overlayIcon1Click: () -> Unit = {},
    overlayIcon2Click: () -> Unit = {},
    onItemClick: () -> Unit = {},
    isVisibility: Boolean = false,
) {

    var isOverlayViewVisible by remember {
        mutableStateOf(isVisibility)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.size_100)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onItemClick()
            }
            .padding(
                horizontal = Dimens.size_5, vertical = Dimens.size_5
            )
            .clip(RoundedCornerShape(Dimens.size_12)),

        border = BorderStroke(Dimens.size_1, AppTheme.colorScheme.ivaBackgroundScreen)
    ) {

        //box will handle the overlay view and the main view
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.size_10))
                    .fillMaxWidth()
                    .height(Dimens.size_100)
                    .background(AppTheme.colorScheme.ivaWhiteBackground)
            ) {

                val (iconStartRef, textColumnRef, iconEndRef, dotLine) = createRefs()

                //region start icon
                if (frequentUiModel.startIcon != 0)
                    Icon(
                        painter = painterResource(
                            id = frequentUiModel.startIcon ?: R.drawable.ic_iva_placeholder
                        ),
                        contentDescription = "start icon",
                        modifier = Modifier
                            .padding(Dimens.size_14)
                            .constrainAs(
                                iconStartRef
                            ) {
                                start.linkTo(parent.start)
                                end.linkTo(dotLine.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                isOverlayViewVisible = true
                            }
                            .size(Dimens.size_20)
                            .alpha(if (frequentUiModel.startIcon == null) 0f else 1f)
                    )
                //endregion

                //region Vertical dotted line
                VerticalDottedLine(modifier = Modifier
                    .width(Dimens.size_1)
                    .height(Dimens.size_100)
                    .constrainAs(
                        dotLine
                    ) {
                        start.linkTo(iconStartRef.end, margin = Dimens.size_8)
                        top.linkTo(iconStartRef.top)
                        bottom.linkTo(iconStartRef.bottom)
                    })
                //endregion


                //region texts
                Column(
                    modifier = Modifier.constrainAs(
                        textColumnRef

                    ) {
                        end.linkTo(iconEndRef.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    // first text
                    Text(
                        text = frequentUiModel.firstText,
                        modifier = Modifier
                            .padding(
                                start = Dimens.size_8,
                                end = Dimens.size_8
                            ),
                        style = AppTheme.typography.text_10PX_13SP_B.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // second text
                    Text(
                        text = frequentUiModel.secondText,
                        modifier = Modifier
                            .padding(
                                start = Dimens.size_8,
                                end = Dimens.size_8
                            ),
                        style = AppTheme.typography.text_10PX_13SP_M
                    )


                    frequentUiModel.thirdText?.let {
                        // third text
                        if (it.isNotEmpty())
                            Text(
                                text = frequentUiModel.thirdText,
                                modifier = Modifier
                                    .padding(
                                        start = Dimens.size_8,
                                        end = Dimens.size_8
                                    ),
                                style = AppTheme.typography.text_10PX_13SP_M

                            )
                    }
                }
                //endregion


                //region end icon
                if (frequentUiModel.endIcon != 0 || !frequentUiModel.endIconURL.isNullOrEmpty())
                    UnsafeImageApp(
                        darkLogo = frequentUiModel.endIconURL,
                        lightLogo = frequentUiModel.endIconURL,
                        placeholder = R.drawable.ic_feature,
                        modifier = Modifier
                            .size(Dimens.size_40, Dimens.size_40)
                            .padding(end = Dimens.size_8)
                            .constrainAs(
                                iconEndRef
                            ) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        contentDescription = "Item",
                    )
            }

            //region overlay view
            this@Card.AnimatedVisibility(
                visible = isOverlayViewVisible,
                enter = expandHorizontally(tween(durationMillis = 1000)),
                exit = shrinkHorizontally(tween(durationMillis = 1000))
            ) {
                Box(
                    modifier = Modifier
                        .background(AppTheme.colorScheme.ivaBackgroundButton2)
                ) {
                    Image(modifier = Modifier
                        .padding(start = Dimens.size_14)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isOverlayViewVisible = false
                        }
                        .size(Dimens.size_20)
                        .align(Alignment.CenterStart),
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "")


                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,

                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Box(
                            modifier = Modifier
                                .padding(
                                    top = Dimens.size_20,
                                    bottom = Dimens.size_20,
                                    end = Dimens.size_25
                                )
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            if (frequentUiModel.overlayIcon1 != 0)
                                Icon(
                                    tint = AppTheme.colorScheme.ivaTitleText3,
                                    painter = painterResource(
                                        id = frequentUiModel.overlayIcon1
                                            ?: R.drawable.ic_iva_placeholder
                                    ),
                                    contentDescription = "remove overlay icon",
                                    modifier = Modifier
                                        .size(Dimens.size_25)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            overlayIcon1Click()
                                        }
                                        .align(Alignment.TopCenter)
                                        .alpha(if (frequentUiModel.overlayIcon1 == null) 0f else 1f),
                                )
                            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                                Text(
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    text = frequentUiModel.overlayText1,
                                    color = AppTheme.colorScheme.ivaTitleText3
                                )
                            }
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(
                                    top = Dimens.size_20,
                                    bottom = Dimens.size_20,
                                    start = Dimens.size_25
                                )
                                .fillMaxHeight()
                        )
                        {
                            if (frequentUiModel.overlayIcon2 != 0)
                                Icon(
                                    tint = AppTheme.colorScheme.ivaTitleText3,
                                    painter = painterResource(
                                        id = frequentUiModel.overlayIcon2
                                            ?: R.drawable.ic_iva_placeholder
                                    ),
                                    contentDescription = "retry overlay icon",
                                    modifier = Modifier
                                        .size(Dimens.size_25)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            overlayIcon2Click()
                                        }
                                        .align(Alignment.TopCenter)
                                        .alpha(if (frequentUiModel.overlayIcon2 == null) 0f else 1f)


                                )
                            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                                Text(
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    text = frequentUiModel.overlayText2,
                                    color = AppTheme.colorScheme.ivaTitleText3
                                )
                            }
                        }
                        Spacer(modifier = Modifier)
                    }
                }

            }
            //endregion
        }


    }
}

@Composable
fun VerticalDottedLine(modifier: Modifier) {
    Canvas(modifier = modifier) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = Dimens.size_1.toPx(),
            pathEffect = pathEffect,
        )
    }
}

@Preview(showBackground = true)
@ThemePreviews
@Composable
fun PreviewFrequentComponent() {
    AppTheme {

    }
    FrequentComponent(
        FrequentUiModel(
            startIcon = AppIcons.icMoreBlack,
            endIcon = AppIcons.icSetting,
            overlayIcon1 = AppIcons.icRemove,
            overlayIcon2 = AppIcons.icRetry,
            firstText = "شارژ مستقیم - 92.000 ریال",
            secondText = "09104757572",
            thirdText = "با احتساب 10 درصد مالیات",
            overlayText1 = "حذف",
            overlayText2 = "انجام مجدد"
        ),
        overlayIcon1Click = {},
        overlayIcon2Click = {},
        onItemClick = {},
        isVisibility = false
    )
}