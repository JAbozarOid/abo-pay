package com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.MetaData

//region c2c description content
@Composable
internal fun C2CDescriptionContent(
    modifier: Modifier = Modifier,
    state: MetaData,
    labelTitle: String,
    hintTitle: String,
    onDescriptionValueChanged: (String) -> Unit = {},
) {
    val maxDescLength = 40
    AppTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.size_4,
                vertical = Dimens.size_4
            ),
        textAlign = TextAlign.Start,
        value = state.description.value,
        placeHolder = hintTitle,
        placeHolderAlignment = Alignment.CenterEnd,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            if (it.length <= maxDescLength) {
                onDescriptionValueChanged(it)
            }
        },
        isError = state.description.errorMessage.isNotEmpty(),
        supportingText = state.description.errorMessage,
        label = labelTitle,
    )
}
//endregion