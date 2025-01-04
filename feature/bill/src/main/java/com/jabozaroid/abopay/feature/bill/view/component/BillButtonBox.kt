package com.jabozaroid.abopay.feature.bill.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens


@Composable
internal fun ButtonsBox(
    onClickPaymentWithId: () -> Unit,
    onClickScanId: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                top = Dimens.size_4,
                bottom = Dimens.size_8,
                start = 0.dp
            )
            .background(AppTheme.colorScheme.background)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = Dimens.size_16, end = Dimens.size_0)

        ) {
            AppOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickScanId,
                text = {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(
                            text = aboPayStringResource(id = R.string.scan_with_id),
                            maxLines = 1,
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(id = com.jabozaroid.abopay.feature.bill.R.drawable.scaner_icon),
                        contentDescription = null
                    )
                },
            )
        }
        Spacer(modifier = Modifier.width(Dimens.size_4))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth()
                .padding(end = Dimens.size_16, start = Dimens.size_4)
        ) {
            AppButton(modifier = Modifier.fillMaxWidth(), onClick = onClickPaymentWithId, text = {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(R.string.pay_with_reference),
                        maxLines = 1,
                    )
                }

            })
        }
    }
}

