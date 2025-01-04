package com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel

//region c2c data entry content
@Composable
internal fun C2CInputValueContent(
    modifier: Modifier,
    state: CardToCardUiModel,
    onCardValueChanged: (String) -> Unit,
    onPriceValueChanged: (String) -> Unit,
    onDescriptionValueChanged: (String) -> Unit,
    onCardIconClicked: () -> Unit,
    cardNumber: MutableState<String> = mutableStateOf(""),
    onClearCardClicked: () -> Unit = {}
) {

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        C2CDestinationContent(state = state,
            onIconClicked = onCardIconClicked,
            cardNumber = cardNumber,
            onChangeCardTextFiled = { onCardValueChanged(it) },
            onClearCardClicked = onClearCardClicked
        )

        C2CAmountContent(state, onPriceValueChanged)
        C2CDescriptionContent(
            state = state.metaData,
            onDescriptionValueChanged = onDescriptionValueChanged,
            labelTitle = aboPayStringResource(R.string.enter_description),
            hintTitle = aboPayStringResource(R.string.description)
        )

    }
}
//endregion