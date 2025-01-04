package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun InternetConnectionComponent(
    modifier: Modifier = Modifier,
    message: String = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.data_receive_error_message),
    onRefreshClicked: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.ivaWhiteBackground
        ),
        shape = RoundedCornerShape(Dimens.size_12)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.size_15),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.internet_connection),
                contentDescription = "refresh"
            )
            Text(
                color = AppTheme.colorScheme.ivaOutlineButtonText,
                modifier = Modifier.padding(
                    horizontal = Dimens.size_70,
                    vertical = Dimens.size_8
                ),
                style = AppTheme.typography.text_9PX_12SP_M.copy(
                    textDirection = TextDirection.Rtl,
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                text = message
            )
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_70,
                        vertical = Dimens.size_8
                    ),

                onClick = {
                    onRefreshClicked()
                },
                enabled = true
            ) {
                Text(
                    text = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.try_again),
                    style = AppTheme.typography.text_12PX_16SP_M
                )
            }


        }
    }
}

@Preview
@Composable
@ThemePreviews
fun PreviewInternetConnection() {
    AppTheme {
        InternetConnectionComponent(modifier = Modifier) { }
    }
}