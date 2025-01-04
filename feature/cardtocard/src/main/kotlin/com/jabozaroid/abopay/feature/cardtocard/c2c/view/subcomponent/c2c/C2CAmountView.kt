package com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.model.CurrencyType
import com.jabozaroid.abopay.core.common.util.CurrencyAmountInputVisualTransformation
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel

//region c2c amount content
@Composable
internal fun C2CAmountContent(
    state: CardToCardUiModel,
    onPriceValueChanged: (String) -> Unit,
) {
    val amount = rememberSaveable { mutableStateOf("") }
    val maxAmountLength = 9
    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.size_4,
                vertical = Dimens.size_4
            ),
        value = state.metaData.amount.value,
        placeHolder = aboPayStringResource(R.string.enter_price),
        visualTransformation = CurrencyAmountInputVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {

            if (it.length <= maxAmountLength) {
                if (!it.startsWith("0")) {
                    onPriceValueChanged(it)
                    amount.value = it
                }
            }
        },
        isError = !state.metaData.amount.errorMessage.aboPayStringResource().isNullOrEmpty(),
        supportingText = if (state.metaData.amount.errorMessage.aboPayStringResource().isNullOrBlank())
            FormatUtil.convertAmount(
                state.metaData.amount.value,
                CurrencyType.RIAL
            )
        else
            null,
        label = aboPayStringResource(R.string.amount_rial),
    )
}
//endregion