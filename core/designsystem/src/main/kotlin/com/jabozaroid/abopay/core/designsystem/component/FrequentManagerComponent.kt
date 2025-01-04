package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

//region frequent content
@Composable
fun FrequentManagerComponent(
    modifier: Modifier = Modifier,
    frequentItems: MutableList<FrequentUiModel> = mutableListOf(),
    onFrequentRemoveIconClicked: (FrequentUiModel) -> Unit,
    onFrequentEditIconClicked: (FrequentUiModel) -> Unit,
    title: String = "",
    description: String = "",
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.End),
            color = AppTheme.colorScheme.ivaOutlineButtonText,
            style = AppTheme.typography.text_11PX_15SP_B
        )

        //condition frequent list is empty or not
        if (frequentItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = description,
                    color = AppTheme.colorScheme.ivaOutlineButtonText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = AppTheme.typography.text_10PX_13SP_M.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W500
                    )

                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(frequentItems) { item ->
                    FrequentComponent(
                        frequentUiModel = item,
                        overlayIcon1Click = {
                            onFrequentRemoveIconClicked(
                                item
                            )
                        }, overlayIcon2Click = {
                            onFrequentEditIconClicked(
                                item
                            )
                        }
                    )
                }
            }
        }
    }
}
//endregion