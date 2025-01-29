package com.jabozaroid.abopay.core.designsystem.component.messenger

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun MessengerToolbar(
    modifier: Modifier = Modifier,
    title: String = "",
    firstIcon: Int = R.drawable.ic_search,
    secondIcon: Int = R.drawable.ic_more_black,
    onFirstIconClicked: () -> Unit = {},
    onSecondIconClicked: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = Dimens.size_8,
                vertical = Dimens.size_10
            )
            .clip(RoundedCornerShape(Dimens.size_12)),
        tonalElevation = Dimens.size_4,
        shadowElevation = Dimens.size_4,
        color = AppTheme.colorScheme.messengerDarkBackground,
        border = BorderStroke(0.25.dp, AppTheme.colorScheme.surface)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.size_10))
        ) {

            val (titleRef, firstIconRef, secondIconRef) = createRefs()

            Text(
                text = title,
                textAlign = TextAlign.Start,
                modifier = Modifier.constrainAs(titleRef) {
                    start.linkTo(parent.start, Dimens.size_8)
                    top.linkTo(parent.top, Dimens.size_8)
                    bottom.linkTo(parent.bottom, Dimens.size_8)
                    width = Dimension.fillToConstraints
                },
                color = AppTheme.colorScheme.aboWhiteBackground,
                style = AppTheme.typography.text_13PX_17SP_M.copy(
                    fontWeight = FontWeight.W800
                ),
            )

            Image(
                modifier = Modifier
                    .constrainAs(
                        firstIconRef
                    ) {
                        end.linkTo(secondIconRef.start, Dimens.size_8)
                        top.linkTo(parent.top, Dimens.size_8)
                        bottom.linkTo(parent.bottom, Dimens.size_8)
                        width = Dimension.value(Dimens.size_20)
                        height = Dimension.value(Dimens.size_20)
                    }
                    .clickable {
                        onFirstIconClicked()
                    },
                painter = painterResource(id = firstIcon),
                contentDescription = "search icon",
                colorFilter = ColorFilter.tint(AppTheme.colorScheme.aboWhiteBackground)
            )
            Image(
                modifier = Modifier
                    .constrainAs(
                        secondIconRef,
                    ) {
                        end.linkTo(parent.end, Dimens.size_8)
                        top.linkTo(parent.top, Dimens.size_8)
                        bottom.linkTo(parent.bottom, Dimens.size_8)
                        width = Dimension.value(Dimens.size_20)
                        height = Dimension.value(Dimens.size_20)
                    }
                    .clickable {
                        onSecondIconClicked()
                    },
                painter = painterResource(id = secondIcon),
                contentDescription = "more icon",
                colorFilter = ColorFilter.tint(AppTheme.colorScheme.aboWhiteBackground)
            )


        }
    }

}

@ThemePreviews
@Composable
fun MessengerToolbarPreview() {
    AppTheme {
        MessengerToolbar(
            title = "AboPayMessenger",
            firstIcon = R.drawable.ic_search,
            secondIcon = R.drawable.ic_more_black
        )
    }

}