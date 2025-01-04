package com.jabozaroid.abopay.feature.cardtocard

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.model.MetaDataModel
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

/**
 *  Created on 10/23/2024
 **/


@Composable
fun KeyValueRow(
    item: com.jabozaroid.abopay.core.common.model.MetaDataModel,
    keyTextStyle: TextStyle = AppTheme.typography.text_10PX_13SP_M.copy(fontWeight = FontWeight.Bold),
    valueTextStyle: TextStyle = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Rtl)
) {

    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween)
    {
        Text(
            text = item.value,
            style = keyTextStyle.copy(
                textDirection = TextDirection.Ltr
            ),
            textAlign = TextAlign.Left,
            modifier = Modifier
                .basicMarquee()
                .padding(all = Dimens.size_5)
                .weight(0.55f),
            maxLines = 1,
            color = AppTheme.colorScheme.ivaTitleText
        )
        Text(
            text = item.key,
            style = valueTextStyle,
            modifier = Modifier
                .basicMarquee()
                .padding(all = Dimens.size_5)
                .weight(0.4f),
            maxLines = 1,
            textAlign = TextAlign.Right,
            color = AppTheme.colorScheme.ivaTitleText
        )
    }}


@Preview
@Composable
fun Preview() {
    AppTheme {
        AppBackground(modifier = Modifier) {}
        KeyValueRow(
            item = MetaDataModel(
                key = "شماره کارت مشتری  ",
                value = "60379971756076306565656565"
            )
        )
    }

}