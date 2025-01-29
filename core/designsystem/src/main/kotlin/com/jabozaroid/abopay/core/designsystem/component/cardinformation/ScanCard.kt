package com.jabozaroid.abopay.core.designsystem.component.cardinformation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

/**
 * Created on 26,November,2024
 */

@Composable
fun ScanCard(
    modifier: Modifier = Modifier,
    scanCardVisibility: Boolean = true,
    onScanCardClicked: () -> Unit,
) {
    if (scanCardVisibility) {
        AppOutlinedButton(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {

            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.scan_card),
                    contentDescription = null
                )
            }, text = {
                Text(
                    aboPayStringResource(id = R.string.scan_card),
                    style = AppTheme.typography.text_12PX_16SP_M.copy(
                        color = AppTheme.colorScheme.aboTitleText
                    )
                )
            }
        )
    }
}

@Preview
@Composable
@ThemePreviews
fun PreviewScanCard() {
    AppTheme {
        ScanCard {

        }
    }
}