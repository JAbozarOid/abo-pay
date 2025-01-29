package com.jabozaroid.abopay.feature.internet.view.subcomponent

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.FrequentComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.internet.model.InternetUiModel

//region frequent content
@Composable
internal fun FrequentInternetContent(
    state: InternetUiModel,
    onShowTopUpInternetsBottomSheet: (phoneNumber: String) -> Unit = {},
    onFrequentListHasItem: () -> Unit,
    onFrequentRemoveIconClicked: (index: Int, list: MutableList<FrequentUiModel>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = Dimens.size_16,
                end = Dimens.size_16,
                top = Dimens.size_8,
                bottom = Dimens.size_8
            ),
    ) {
        ProvideTextStyle(value = AppTheme.typography.text_11PX_15SP_B) {
            Text(
                text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.frequent_package),
                modifier = Modifier.align(Alignment.End),
                color = AppTheme.colorScheme.aboOutlineButtonText
            )
        }

        LaunchedEffect(1) {
            onFrequentListHasItem()
        }


        //condition frequent list is empty or not
        if (state.frequentItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_11PX_15SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.no_package_alert),
                        color = AppTheme.colorScheme.aboOutlineButtonText,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Log.d("ABOOOOZAARR", "FrequentInternetContent: ${state.frequentItems}")
                itemsIndexed(state.frequentItems) { index, item ->
                    FrequentComponent(
                        frequentUiModel = item,
                        overlayIcon1Click = {
                            onFrequentRemoveIconClicked(
                                index,
                                state.frequentItems
                            )
                        }
                    )
                }
            }
        }

        AppButton(
            enabled = state.isContinueEnable(),
            onClick = {
                onShowTopUpInternetsBottomSheet(state.getMobileNumberValue())
            },
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .fillMaxWidth()
        ) {
            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                Text(aboPayStringResource(id = R.string.continue_title))
            }
        }
    }
}
//endregion

@Preview(showBackground = true)
@ThemePreviews
@Composable
//@DevicePreviews
internal fun Preview() {

    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        FrequentInternetContent(state = InternetUiModel(),
            onFrequentRemoveIconClicked = {index: Int, list: MutableList<FrequentUiModel> ->  },
            onFrequentListHasItem = {})

    }
}

