package com.jabozaroid.abopay.feature.payment.pay.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.SearchComponent
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme


@Composable
internal fun WalletContent(modifier: Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
            Text(
                textAlign = TextAlign.Center,
                text = aboPayStringResource(R.string.not_available_service_message),
                color = AppTheme.colorScheme.ivaTitleText,
            )
        }


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ShowCardBottomSheet(
    dismiss: () -> Unit,
    list: List<SearchItemModel>,
    onCardSelected: (SearchItemModel) -> Unit,
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenSize = (screenHeight - 20).dp
    BottomSheetComponent(
        onDismiss = { dismiss() },
        title = aboPayStringResource(id = R.string.my_cards),
        content = {
            Box(modifier = Modifier.height(screenSize)) {
                SearchComponent(
                    searchItems = list,
                    onItemSelect = {
                        dismiss()
                        onCardSelected(it)
                    })
            }
        },
        isScrollable = true,
        hasMaxHeight = true
    )
}