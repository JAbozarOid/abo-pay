package com.jabozaroid.abopay.core.designsystem.component.cardplaceholder

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jabozaroid.abopay.core.common.util.InputVisualTransformation
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@SuppressLint("SuspiciousIndentation", "ResourceAsColor")
@Composable
fun CardPlaceHolder(
    modifier: Modifier = Modifier,
    bankName: Int? = null,
    onCardSelected: (Int) -> Unit = {},
    index: Int = -1,
    isSelected: Boolean = true,
    bankIcon: Int? = null,
    cardNumber: String? = null,
    ownerName: String? = null,
    scale: Float,
    month: String? = null,
    year: String? = null,
    cardValidationLogo: Int = R.drawable.authorized_icon,
    defaultCardLogo: Int = R.drawable.default_icon,
    isValidateVisible: Boolean = false,
    isDefaultVisible: Boolean = false,
    cardColorUp: Int? = null,
    cardColorDown: Int? = null,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val height = ((screenHeight / 4) - 10)

        if (isSelected) onCardSelected(index)

        val alpha = animateFloatAsState(
            targetValue = if (isSelected) 1f else 0.2f,
            animationSpec = tween(450, easing = LinearEasing), label = ""
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .scale(scale)
                .alpha(alpha.value)
                .padding(
                    horizontal = Dimens.size_8,
                    vertical = Dimens.size_8
                ),
        ) {


            Column(
                modifier = if (cardColorUp != null && cardColorDown != null) {
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    colorResource(cardColorUp),
                                    colorResource(cardColorDown)
                                )
                            )
                        )
                        .paint(
                            painterResource(com.jabozaroid.abopay.core.common.R.drawable.card_pattern),
                            contentScale = ContentScale.FillBounds
                        )
                        .clip(RoundedCornerShape(Dimens.size_12))
                        .padding(Dimens.size_10)
                } else {
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            colorResource(com.jabozaroid.abopay.core.common.R.color.base)
                        )
                        .paint(
                            painterResource(com.jabozaroid.abopay.core.common.R.drawable.card_pattern),
                            contentScale = ContentScale.FillBounds
                        )
                        .clip(RoundedCornerShape(Dimens.size_12))
                        .padding(Dimens.size_10)

                }
            ) {
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp
                val textFiledWidth = (screenWidth / 4)
                val cardUserNameTextFiledWidth = (screenWidth / 3)
                val textFiledHeight = height / 5
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    if (bankIcon == null)
                        Box(
                            modifier = Modifier
                                .padding(
                                    start = Dimens.size_4,
                                    end = Dimens.size_4
                                )
                                .clip(RoundedCornerShape(textFiledHeight + 40))
                                .size(textFiledHeight.dp)
                                .background(color = AppTheme.colorScheme.background)
                        )
                    if (bankIcon != null)
                        Image(
                            modifier = Modifier
                                .padding(Dimens.size_8)
                                .size(Dimens.size_24),
                            painter =
                            rememberAsyncImagePainter(
                                model = bankIcon,
                                placeholder = painterResource(id = com.jabozaroid.abopay.core.common.R.drawable.ic_card_design_system),
                                contentScale = ContentScale.Inside
                            ),
                            contentDescription = "bankIcon"
                        )
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .defaultMinSize(
                                minWidth = cardUserNameTextFiledWidth.dp,
                                minHeight = textFiledHeight.dp
                            )
                            .width(textFiledWidth.dp)
                            .clip(RoundedCornerShape(Dimens.size_12))
                            .background(color = if (bankName == null) AppTheme.colorScheme.background else Color.Unspecified)
                    ) {
                        if (bankName != null)
                            Text(
                                modifier = Modifier
                                    .padding(vertical = Dimens.size_10)
                                    .align(Alignment.CenterStart),
                                text = aboPayStringResource(id = bankName),
                                color = AppTheme.colorScheme.background,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                style = AppTheme.typography.text_11PX_15SP_M
                            )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(horizontal = Dimens.size_4)
                                .size(Dimens.size_24)
                                .alpha(if (isValidateVisible) 1f else 0f),
                            painter =
                            rememberAsyncImagePainter(
                                model = cardValidationLogo,
                                placeholder = painterResource(id = R.drawable.authorized_icon)
                            ),
                            contentDescription = "bankIcon"
                        )
                        Image(
                            modifier = Modifier
                                .padding(start = Dimens.size_4)
                                .size(Dimens.size_24)
                                .alpha(if (isDefaultVisible) 1f else 0f),
                            painter =
                            rememberAsyncImagePainter(
                                model = defaultCardLogo,
                                placeholder = painterResource(id = R.drawable.default_icon)
                            ),
                            contentDescription = "bankIcon"
                        )
                    }
                }

                Box()
                {
                    val mask =
                        aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.card_visual_transformation_mask)
                    val visualTransformation = remember {
                        InputVisualTransformation(mask, '0')
                    }
                    val defaultPlaceHolder =
                        aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.add_new_card)
                    val transformedText = remember(cardNumber) {
                        visualTransformation.filter(
                            AnnotatedString(
                                cardNumber ?: defaultPlaceHolder
                            )
                        )
                    }.text
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.size_8),
                        text = if (cardNumber.isNullOrEmpty()) "----  ----  ----  ----" else transformedText.text,
                        color = AppTheme.colorScheme.background,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.text_15PX_20SP_B.copy(
                            textDirection = TextDirection.Ltr,

                            )

                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.size_8),
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {
                    Box(
                        modifier = Modifier
                            .defaultMinSize(minWidth = cardUserNameTextFiledWidth.dp)
                            .height(textFiledHeight.dp)
                            .clip(RoundedCornerShape(Dimens.size_12))
                            .background(color = if (ownerName == null) AppTheme.colorScheme.background else Color.Unspecified)
                    ) {
                        if (ownerName != null)
                            Text(
                                modifier = Modifier
                                    .padding(top = Dimens.size_10)
                                    .wrapContentSize()
                                    .align(Alignment.CenterStart),
                                text = ownerName,
                                color = AppTheme.colorScheme.background,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                style = AppTheme.typography.text_11PX_15SP_M.copy(
                                    textDirection = TextDirection.Rtl
                                ),
                            )
                    }
                    Row {
                        Box(
                            modifier = Modifier
                                .width(Dimens.size_40)
                                .height(textFiledHeight.dp)
                                .clip(RoundedCornerShape(Dimens.size_12))
                                .background(color = if (month == null) AppTheme.colorScheme.background else Color.Unspecified)
                        ) {
                            if (month != null)
                                Text(
                                    modifier = Modifier
                                        .padding(top = Dimens.size_10)
                                        .fillMaxSize(),
                                    text = month,
                                    color = AppTheme.colorScheme.background,
                                    textAlign = TextAlign.Left,
                                    style = AppTheme.typography.text_11PX_15SP_M,
                                )

                        }

                        Text(
                            modifier = Modifier
                                .padding(horizontal = Dimens.size_4)
                                .padding(top = Dimens.size_16)
                                .wrapContentSize(),
                            text = "/",
                            color = AppTheme.colorScheme.background,
                            textAlign = TextAlign.Start,
                        )
                        Box(
                            modifier = Modifier
                                .width(Dimens.size_40)
                                .height(textFiledHeight.dp)
                                .clip(RoundedCornerShape(Dimens.size_12))
                                .background(color = if (year == null) AppTheme.colorScheme.background else Color.Unspecified)
                        ) {
                            if (year != null)
                                Text(
                                    modifier = Modifier
                                        .padding(top = Dimens.size_10)
                                        .fillMaxSize(),
                                    text = year,
                                    color = AppTheme.colorScheme.background,
                                    textAlign = TextAlign.Right,
                                    style = AppTheme.typography.text_11PX_15SP_M,
                                )

                        }

                    }

                }
            }

        }
    }
}

@Preview
@Composable
@ThemePreviews
fun PreviewCardPlaceHolder() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize())
        {
            CardPlaceHolder(
                bankName = null,
                bankIcon = null,
                cardNumber = null,
                scale = 1f,
                onCardSelected = {}
            )
            CardPlaceHolder(
                bankName = com.jabozaroid.abopay.core.common.R.string.kosar_bank,
                bankIcon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_color_mellat,
                cardNumber = "۶۰۳۷۹۹۸۱۷۰۴۴۷۷۵۵",
                month = "10",
                year = "1409",
                ownerName = "ساناز رمضانپور آهنگ",
                isDefaultVisible = true,
                isValidateVisible = true,
                scale = 1f,

                )
        }
    }
}