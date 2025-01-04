package com.jabozaroid.abopay.feature.balance.view.componen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
internal fun TitleReceiptContent(modifier: Modifier) {

    Column(modifier = modifier.padding(all = Dimens.size_8), Arrangement.Center) {

        Image(
            modifier = Modifier
                .height(Dimens.size_70)
                .width(Dimens.size_70)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = Dimens.size_24),
            painter = painterResource(
                id = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_done
            ),
            contentDescription = " icon"
        )

            Text(
                modifier = Modifier.padding(vertical = Dimens.size_16),
                text = aboPayStringResource(R.string.success_balance_receipt_message),
                color = AppTheme.colorScheme.success,
                style = AppTheme.typography.text_12PX_16SP_B,
                maxLines = 1
            )
        }

}