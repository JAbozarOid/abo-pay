package com.jabozaroid.abopay.feature.balance.view.componen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.balance.R


@Composable
internal fun BalanceDescription(
    modifier: Modifier, state: String?,
    onClose: () -> Boolean = { false },
) {
    var isVisible = remember {
        !onClose()
    }
    if (isVisible) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(AppTheme.colorScheme.ivaBalanceInformationBackground),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            Image(
                painter = painterResource(R.drawable.cross),
                contentDescription = "Info",
                modifier = Modifier
                    .padding(start = Dimens.size_8)
                    .size(Dimens.size_10)
                    .align(Alignment.CenterVertically)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { isVisible = onClose() },

                )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimens.size_8,
                        horizontal = Dimens.size_8
                    )
                    .weight(0.7f)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = AbsoluteAlignment.Right
            ) {

                SpannableText(state ?: "")

                Spacer(modifier = Modifier.height(Dimens.size_8))
                Text(
                    text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.compatible_owner_sim),
                    color = AppTheme.colorScheme.ivaTitleText,
                    textAlign = TextAlign.Right,
                    style = AppTheme.typography.text_10PX_13SP_M
                )
            }
            Image(
                painter = painterResource(R.drawable.balance_icon_info),
                contentDescription = "Info",
                modifier = Modifier
                    .padding(end = Dimens.size_8)
                    .size(20.dp)
                    .align(Alignment.CenterVertically),

                )
        }
    }

}


@ThemePreviews
@Preview(showBackground = true)
@Composable
fun PreviewTopDescriptionPage() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            BalanceDescription(Modifier, state = "144 تومان", onClose = { true })
        }
    }
}