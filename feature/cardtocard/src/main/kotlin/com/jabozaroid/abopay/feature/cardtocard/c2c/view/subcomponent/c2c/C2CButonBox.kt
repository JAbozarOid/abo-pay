package com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.SwitchWithLabel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.cardtocard.R
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel

/**
 *  Created on 11/2/2024
 **/


@Composable
internal fun ButtonBox(
    state: CardToCardUiModel,
    modifier: Modifier,
    onSaveSwitchStateChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)

        ) {
            AppOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                text = {
                    Text(
                        style = AppTheme.typography.text_9PX_12SP_B,
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.frequent_transactions),
                        maxLines = 1,
                    )

                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(id = R.drawable.ic_transaction),
                        contentDescription = null
                    )
                },
            )
        }
        Spacer(modifier = Modifier.width(Dimens.size_16))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            SwitchWithLabel(
                label = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.save_destination_card),
                state = state.metaData.saveDestinationCard,
                onStateChange = onSaveSwitchStateChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimens.size_8,
                        horizontal = Dimens.size_4
                    )

            )

        }
    }
}