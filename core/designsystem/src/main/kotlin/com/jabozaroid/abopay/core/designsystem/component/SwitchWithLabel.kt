package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.common.R

/**
 *  Created on 8/29/2024 
 **/

@Composable
fun SwitchWithLabel(
    modifier: Modifier = Modifier,
    label: String, state: Boolean,
    onStateChange: (Boolean) -> Unit,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedTrackColor = AppTheme.colorScheme.primary,
        uncheckedTrackColor = AppTheme.colorScheme.ivaGraySwitchSelected,
        uncheckedBorderColor = AppTheme.colorScheme.ivaGraySwitchSelected,
        uncheckedThumbColor = AppTheme.colorScheme.ivaWhiteBackground
    ),
) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                // This is for removing ripple when Row is clicked
                indication = null,
                role = Role.Switch,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(Dimens.size_0),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            style = AppTheme.typography.text_9PX_12SP_M.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colorScheme.ivaTitleText
        )

        Switch(
            modifier = Modifier
                .padding(start = Dimens.size_4)
                .scale(0.8f),
            colors = colors,
            checked = state,
            onCheckedChange = {
                onStateChange(it)
            }
        )
    }
}


@Composable
@Preview
private fun Preview() {
    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        SwitchWithLabel(label = aboPayStringResource(id = R.string.save_in_frequent_transactions),
            state = true, onStateChange = {})

    }
}