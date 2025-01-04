package com.jabozaroid.abopay.feature.login.view.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

/**
 * Created on 01,January,2025
 */


@Composable
fun ShowReagentBottomSheet(onDismiss: () -> Unit) {

    BottomSheetComponent(
        title = aboPayStringResource(id = R.string.enter_reagent_code),
        content = {
            BottomSheetContainer()
        },
        onDismiss = onDismiss,
        showDefaultButtons = true,
        onBtn1Click = onDismiss,
        onBtn2Click = onDismiss
    )

}

@Composable
fun BottomSheetContainer() {
    var reagentCode by rememberSaveable { mutableStateOf("") }

    AppTextField(
        value = reagentCode,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { reagentCode = it },
        label = aboPayStringResource(id = R.string.reagent_code),
        modifier = Modifier
            .padding(Dimens.size_18)
            .fillMaxWidth()
            .wrapContentHeight(),
        placeHolder = aboPayStringResource(id = R.string.reagent_code_place_holder),
        placeHolderAlignment = Alignment.Center
    )

}

@Preview(showBackground = true)
@Composable
fun BottomSheetContainerPreview() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            BottomSheetContainer()
        }

    }
}