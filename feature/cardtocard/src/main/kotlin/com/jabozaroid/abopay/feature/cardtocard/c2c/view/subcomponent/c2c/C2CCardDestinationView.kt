package com.jabozaroid.abopay.feature.cardtocard.c2c.view.subcomponent.c2c

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.InputVisualTransformation
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.cardtocard.R
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel

//region c2c destination content
@Composable
internal fun C2CDestinationContent(
    modifier: Modifier = Modifier,
    state: CardToCardUiModel,
    onIconClicked: () -> Unit,
    onChangeCardTextFiled: (String) -> Unit,
    cardMask: String = "0000  0000  0000  0000",
    maskNumber: Char = '0',
    cardNumber: MutableState<String> = mutableStateOf(""),
    isCardIconActive: Boolean = true,
    onClearCardClicked: () -> Unit = {},
) {
    val savableCardNumber = rememberSaveable {
        cardNumber
    }
    AppTextField(
        textDirection = TextDirection.Ltr,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.size_4),
        value = savableCardNumber.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

        onValueChange = {
            savableCardNumber.value = it.take(cardMask.count { char ->
                char == maskNumber
            })
            onChangeCardTextFiled(it.take(cardMask.count { char ->
                char == maskNumber
            }))

        },
        visualTransformation = InputVisualTransformation(cardMask, maskNumber),
        label = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.destination_card),
        isError = !state.destinationCard.errorMessage.aboPayStringResource().isNullOrBlank(),
        supportingText = state.destinationCard.errorMessage.aboPayStringResource(),
        placeHolder = "- - - -    - - - -    - - - -    - - - -",
        leadingIcon = {
            if (isCardIconActive)
                Icon(painter = painterResource(id = R.drawable.ic_card),
                    contentDescription = "leading icon",
                    modifier = modifier
                        .size(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onIconClicked()
                        })
        },

        trailingIcon = {
            state.destinationCard.icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "leading icon",
                    modifier = modifier
                        .size(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onClearCardClicked()
                        },
                    contentScale = ContentScale.Inside
                )
            }

        })
}
//endregion


@Preview
@Composable
@ThemePreviews
fun Preview() {

    AppTheme {
        AppBackground(modifier = Modifier) {}
        C2CDestinationContent(state = CardToCardUiModel(),
            modifier = Modifier,
            onClearCardClicked = {},
            onChangeCardTextFiled = {},
            isCardIconActive = true,
            onIconClicked = {}
        )
    }
}
