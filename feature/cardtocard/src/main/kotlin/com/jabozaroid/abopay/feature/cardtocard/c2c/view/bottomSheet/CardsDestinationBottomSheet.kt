package com.jabozaroid.abopay.feature.cardtocard.c2c.view.bottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.SearchComponent
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.common.R

//region card destinations bottom sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsDestinationBottomSheet(
    dismiss: () -> Unit,
    onCardSelected: (SearchItemModel) -> Unit,
    itemList: List<SearchItemModel>,
    leftIcon: Int,
    onLeftIconClicked: (SearchItemModel) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenSize = (screenHeight - 20).dp
    BottomSheetComponent(
        title = aboPayStringResource(id = R.string.destination_cards),
        onDismiss = { dismiss() },
        content = {
            Box(modifier = Modifier.height(screenSize)) {
                SearchComponent(
                    searchItems = itemList,
                    onItemSelect = {
                        dismiss()
                        onCardSelected(it)
                    }, leftIcon = leftIcon,
                    onLeftIconClicked = onLeftIconClicked
                )
            }
        },
        isScrollable = true,
        hasMaxHeight = true
    )
}
//endregion