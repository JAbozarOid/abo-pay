package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.jabozaroid.abopay.core.designsystem.component.model.PhoneNumberItem
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

//region user charges favourite numbers
@Composable
internal fun UserFavouriteMobilesContent(
    list: List<PhoneNumberItem>,
    phoneNumberStateValue: MutableState<String>,
    onDeleteUserFavoritePhoneNumber: (value: String) -> Unit = {},
    onPhoneNumberValidate: (value: String) -> Unit = {},
    onFindOperatorIndexByUserInput: (value: String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = Dimens.size_18)
            .clip(RoundedCornerShape(Dimens.size_8))
            .background(AppTheme.colorScheme.background)
            .border(
                BorderStroke(
                    width = Dimens.size_1,
                    color = AppTheme.colorScheme.aboBackgroundScreen
                )
            ),
    ) {
        itemsIndexed(list) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimens.size_8,
                        horizontal = Dimens.size_16
                    )
                    .clickable(
                        onClick = {
                            phoneNumberStateValue.value = item.phoneNumber
                            onPhoneNumberValidate(phoneNumberStateValue.value)
                            onFindOperatorIndexByUserInput(phoneNumberStateValue.value)
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // start : cross icon and text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close end icon",
                        modifier = Modifier
                            .size(Dimens.size_20)
                            .padding(start = Dimens.size_4)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onDeleteUserFavoritePhoneNumber(item.phoneNumber)
                            },
                        tint = AppTheme.colorScheme.aboOutlineButtonText
                    )
                    ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                        Text(
                            text = item.phoneNumber,
                            color = AppTheme.colorScheme.aboOutlineButtonText,
                            modifier = Modifier.padding(start = Dimens.size_8)
                        )
                    }
                }

                // spacer to push the two rows to the ends of the parent row
                Spacer(modifier = Modifier.weight(1f))

                // end : text and sim icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                        Text(
                            text = item.ownerPhoneNumber,
                            color = AppTheme.colorScheme.aboOutlineButtonText,
                            modifier = Modifier.padding(end = Dimens.size_8)
                        )
                    }

                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = "start ic operator icon",
                        modifier = Modifier
                            .size(Dimens.size_24)
                            .padding(end = Dimens.size_4)
                    )

                }
            }

            // only show the divider if it's not the last item
            if (index < list.size - 1)
                DividerSection()

        }
    }
}
//endregion

//region divider section
@Composable
internal fun DividerSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.size_16,
                end = Dimens.size_16,
            )
            .height(Dimens.size_1)
            .background(AppTheme.colorScheme.aboBackgroundScreen)
    )
}
//endregion