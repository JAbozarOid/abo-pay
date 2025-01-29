package com.jabozaroid.abopay.feature.cardmanagement.view.subcomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementUiModel
import com.jabozaroid.abopay.feature.cardmanagement.model.MyCardOptionsModel

/**
 * Created on 25,November,2024
 */

@Composable
fun MyCardsOptions(
    state: CardManagementUiModel,
    onActionCopy: (androidx.compose.ui.platform.ClipboardManager, String) -> Unit,
    onDefaultCardSelected: () -> Unit = {},
    onDeleteCardSelected: () -> Unit = {},
    onEditCardSelected: () -> Unit = {},
) {
    val clipboardManager = LocalClipboardManager.current

    CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            contentPadding = PaddingValues(
                vertical = Dimens.size_4,
            ),
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(
                    start = Dimens.size_8,
                    top = Dimens.size_16,
                    end = Dimens.size_16,
                    bottom = Dimens.size_16
                )
        ) {
            items(
                count = state.myCardsActionList.size,
            ) { item ->
                if (item == 0) Spacer(modifier = Modifier.width(Dimens.size_4))
                MyCardActionItem(
                    myCardOptionsModel = state.myCardsActionList[item],
                    onClickAction = {
                        when (it.id) {
                            // ویرایش
                            0 -> {
                                onEditCardSelected()
                            }
                            // مسدودسازی
                            1 -> {}
                            // کپی شماره کارت
                            2 -> {
                                if (state.selectedUserCard.number != null)
                                    onActionCopy(clipboardManager, state.selectedUserCard.number!!)
                            }
                            // پیش فرض
                            3 -> {
                                onDefaultCardSelected()
                            }
                            // احراز هویت
                            4 -> {}
                            // حذف
                            5 -> {
                                onDeleteCardSelected()
                            }
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun MyCardActionItem(
    myCardOptionsModel: MyCardOptionsModel,
    onClickAction: (MyCardOptionsModel) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(
                start = Dimens.size_8, top = Dimens.size_8
            )
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimens.size_8)),
        onClick = { onClickAction(myCardOptionsModel) },
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.aboBackgroundScreen)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.size_8),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(Dimens.size_8))
                    .background(AppTheme.colorScheme.aboWhiteBackground)
                    .padding(Dimens.size_4),
                painter = painterResource(id = myCardOptionsModel.icon),
                contentDescription = aboPayStringResource(
                    id = myCardOptionsModel.title
                ),
                colorFilter = if (myCardOptionsModel.isEnabled)
                    null
                else
                    ColorFilter.tint(AppTheme.colorScheme.aboNoticeTextColor)
            )

            Column {
                Text(
                    modifier = Modifier.padding(start = Dimens.size_8),
                    text = aboPayStringResource(id = myCardOptionsModel.title),
                    style = AppTheme.typography.text_10PX_13SP_M.copy(
                        color = if (myCardOptionsModel.isEnabled)
                            AppTheme.colorScheme.aboTitleText
                        else
                            AppTheme.colorScheme.aboNoticeTextColor
                    ),
                )
                if (myCardOptionsModel.subtitle != null)
                    Text(
                        modifier = Modifier.padding(start = Dimens.size_8),
                        text = aboPayStringResource(id = myCardOptionsModel.subtitle),
                        style = AppTheme.typography.text_8PX_10SP_B.copy(
                            color = if (myCardOptionsModel.isEnabled)
                                AppTheme.colorScheme.aboTitleText
                            else
                                AppTheme.colorScheme.aboNoticeTextColor
                        ),
                    )
            }

        }
    }
}

@Preview(showBackground = true)
@ThemePreviews
@DevicePreviews
@Composable
internal fun PreviewMyCardsActions() {
    AppTheme {
        MyCardsOptions(CardManagementUiModel(), { _, _ -> })
    }
}