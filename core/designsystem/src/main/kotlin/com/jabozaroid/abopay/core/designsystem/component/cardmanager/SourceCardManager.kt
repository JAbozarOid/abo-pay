package com.jabozaroid.abopay.core.designsystem.component.cardmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardplaceholder.CardPlaceHolder
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

@Composable
fun SourceCardManager(
    modifier: Modifier,
    cardList: List<Card>,
    onAddCardIconClicked: () -> Unit,
    onCardSelected: (Card) -> Unit = {},
) {

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy((-30).dp),

        ) {

        UserRegisteredCardsStateHandler(
            cardList = cardList, modifier = Modifier, onCardSelected = onCardSelected
        )

        AddNewCardIcon(modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .align(Alignment.CenterHorizontally)
            .border(
                width = 0.4.dp,
                color = AppTheme.colorScheme.ivaDisableTextFieldOutline,
                shape = CircleShape
            )
            .shadow(
                elevation = 30.dp,
                spotColor = Color.Gray
            )
            .background(AppTheme.colorScheme.ivaWhiteBackground)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onAddCardIconClicked() }
        )

    }
}
//endregion

//region user card state handler -> place holder or list
@Composable
fun UserRegisteredCardsStateHandler(
    modifier: Modifier, cardList: List<Card>, onCardSelected: (Card) -> Unit,
) {
    if (cardList.isEmpty()) {
        CardPlaceHolder(modifier = modifier, scale = 1f)
    } else
        CardList(modifier = modifier, list = cardList, onCardSelected = {
            onCardSelected(it)
        })
}
//end region


@Composable
internal fun AddNewCardIcon(modifier: Modifier) {
    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_add_card),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
        )

    }

}

@Preview
@Composable
@ThemePreviews
fun Preview() {

    AppTheme {
        AppBackground(modifier = Modifier) {}
        Column {
            SourceCardManager(
                modifier = Modifier,
                cardList = listOf(),
                onAddCardIconClicked = {},
                onCardSelected = {})

        }
    }
}