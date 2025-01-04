package com.jabozaroid.abopay.feature.balance.view.componen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

@Composable
internal fun SpannableText(amount: String) {
    val annotatedString = buildAnnotatedString {
        append(aboPayStringResource(id = R.string.inquiry_wage_part1))
        withStyle(
            style = SpanStyle(
                fontFamily = AppTheme.typography.text_10PX_13SP_B.fontFamily,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(amount)
        }
        append(aboPayStringResource(id = R.string.inquiry_wage_part2))
    }
        Text(
            text = annotatedString,
            color = AppTheme.colorScheme.ivaTitleText,
            textAlign = TextAlign.Right,
            style = AppTheme.typography.text_10PX_13SP_B
        )

}