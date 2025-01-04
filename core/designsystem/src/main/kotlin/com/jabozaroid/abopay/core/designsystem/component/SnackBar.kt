package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

/**
 * Created on 14,September,2024
 */

@Stable
@Composable
fun CustomSnackBar(
    message: String,
    snackState: SnackState = SnackState.MESSAGE,
) {
    Snackbar(
        modifier = Modifier
            .padding(vertical = 150.dp, horizontal = Dimens.size_20),
        shape = RoundedCornerShape(Dimens.size_8),
        containerColor = if (snackState == SnackState.MESSAGE) AppTheme.colorScheme.success else AppTheme.colorScheme.error
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = message,
                color = AppTheme.colorScheme.ivaWhiteBackground,
                style = AppTheme.typography.text_10PX_13SP_M.copy(
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
internal fun PreviewCustomSnackBar() {
    AppTheme {
        CustomSnackBar(message = "این یک پیام تست است")
    }
}

enum class SnackState {
    ERROR, MESSAGE
}
