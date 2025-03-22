package com.jabozaroid.abopay.feature.messenger.view.message

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun OwnMessageCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.size_40,
                top = Dimens.size_8,
                bottom = Dimens.size_8,
                end = Dimens.size_8
            )
            .height(Dimens.size_100),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4),
        shape = RoundedCornerShape(Dimens.size_12),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.messengerMessageOwnBg)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}

@Preview(showBackground = true)
@ThemePreviews
@Composable
fun PreviewOwnMessageCard() {
    OwnMessageCard()
}