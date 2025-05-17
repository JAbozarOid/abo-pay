package com.jabozaroid.abopay.feature.finndow.view.shadowing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun RecordButton(
    onRecordIconClick: () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                isClicked = true
                onRecordIconClick()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Record",
                tint = if (isClicked) Color.Blue else Color.Black,
                modifier = Modifier.size(Dimens.size_40)
            )
        }
        Text(
            text = "Record Your\nShadowing",
            style = AppTheme.typography.text_16PX_21SP_M,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}
