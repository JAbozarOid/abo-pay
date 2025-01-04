package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.component.loading.AnimatedPreloader
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

/**
 *  Created on 9/17/2024 
 **/


@Composable
fun ShowLoading() {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x80000000)
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

            AnimatedPreloader(modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center))
        }
    }
}

@Preview
@Composable
@ThemePreviews
private fun showPreView() {
    AppTheme {
        ShowLoading()
    }
}