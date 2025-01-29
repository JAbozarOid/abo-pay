package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun SwitchComponent(
    modifier: Modifier = Modifier,
    titles: List<String>,
    initialSelectedIndex: Int = 0,
    onTitleSelected: (Int) -> Unit,
    cardBackgroundColor: Color = AppTheme.colorScheme.aboSwitchUnselected,
    itemSelectedColor: Color = AppTheme.colorScheme.aboSwitchSelected,
    itemUnselectedColor: Color = AppTheme.colorScheme.aboSwitchUnselected,
    textSelectedColor: Color = Color.Black,
    textUnselectedColor: Color = Color.Gray,
    cardContentPadding: PaddingValues = PaddingValues(Dimens.size_16),
) {

    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimens.size_8)),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        border = BorderStroke(Dimens.size_1, cardBackgroundColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.size_8),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            titles.forEachIndexed { index, title ->
                val isSelected = index == selectedIndex
                val background =
                    if (isSelected) itemSelectedColor else itemUnselectedColor
                val textColor = if (isSelected) textSelectedColor else textUnselectedColor

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Dimens.size_4)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            selectedIndex = index
                            onTitleSelected(index)
                        }
                        .background(
                            color = background,
                            shape = RoundedCornerShape(Dimens.size_8)
                        )
                        .padding(vertical = Dimens.size_8)
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                        Text(
                            text = title, color = textColor, fontSize = 16.sp
                        )
                    }

                }
            }
        }
    }


}

@ThemePreviews
@Composable
fun PreviewSwitchComponent() {
    SwitchComponent(titles = listOf("شگفت انگیز", "عادی"), onTitleSelected = {})
}
