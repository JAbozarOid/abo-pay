package com.jabozaroid.abopay.feature.payment.reciept.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.common.util.share.SharingFileType
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.payment.R
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptUiModel

@Composable
internal fun TitleReceiptContent(modifier: Modifier, state: ReceiptUiModel) {

    Column(modifier = modifier.padding(all = Dimens.size_8), Arrangement.Center) {

        Image(
            modifier = Modifier
                .height(Dimens.size_64)
                .width(Dimens.size_64)
                .align(alignment = Alignment.CenterHorizontally),
            painter = painterResource(
                // todo : there is condition : if Success : show ic_done else ic_error
                id = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_done
            ),
            contentDescription = " icon"
        )


        ProvideTextStyle(
            value = AppTheme.typography.text_14PX_19SP_M.copy(
                fontWeight = FontWeight.Bold,
                textDirection = TextDirection.ContentOrRtl
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = Dimens.size_8),

                // todo : there is condition : if Success : show ic_done else ic_error
                text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.successful_payment),
                color = AppTheme.colorScheme.success
            )
        }
    }
}


@Composable
internal fun MetaDataReceiptContainer(modifier: Modifier, state: ReceiptUiModel) {

    Column(
        modifier = modifier
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(end = Dimens.size_8, start = Dimens.size_24)
            )

            IconContainer(
                state = state, modifier = Modifier
                    .height(56.dp)
                    .width(56.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(start = Dimens.size_8, end = Dimens.size_24)
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(
                    vertical = Dimens.size_16,
                    horizontal = Dimens.size_24
                )
                .fillMaxHeight(),
        ) {
            items(count = state.receiptModel.visualItems.size) { item ->
                KeyValueRow(item = state.receiptModel.visualItems[item])

            }
        }

    }

}

@Composable
internal fun IconContainer(state: ReceiptUiModel, modifier: Modifier) {

    val painter: Painter = if (state.receiptModel.commonItems.iconUrl.isNotBlank()) {

        rememberAsyncImagePainter(
            model = state.receiptModel.commonItems.iconUrl,
            placeholder = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_abo_pay)
        )
    } else painterResource(id = state.receiptModel.commonItems.icon!!)

    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "icon"
    )

}

@Composable
internal fun KeyValueRow(item: com.jabozaroid.abopay.core.common.model.MetaDataModel) {

    Row(
        Modifier.fillMaxWidth(), Arrangement.SpaceBetween
    ) {

            Text(
                text = item.value,
                style = AppTheme.typography.text_10PX_13SP_M.copy(
                    fontWeight = FontWeight.Bold

                ),
                modifier = Modifier.padding(all = Dimens.size_5),
                color = AppTheme.colorScheme.aboTitleText
            )


            Text(
                text = item.key,
                Modifier.padding(all = Dimens.size_5),
                color = AppTheme.colorScheme.aboTitleText
            )

    }

}

@Composable
internal fun ShareContainer(
    modifier: Modifier,
    onShareClicked: (SharingFileType) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = Dimens.size_4),
        Arrangement.Center,
        Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = Dimens.size_24)
                .height(30.dp)
                .width(30.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onShareClicked(SharingFileType.TEXT)
                },
            alignment = Alignment.CenterEnd,
            painter = painterResource(id = R.drawable.ic_copy),
            contentDescription = " icon"
        )
        Image(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onShareClicked(SharingFileType.IMAGE)
                },
            alignment = Alignment.CenterStart,
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = " icon"
        )
        Image(
            modifier = Modifier
                .padding(start = Dimens.size_24)
                .height(32.dp)
                .width(32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onShareClicked(SharingFileType.PDF)
                },
            alignment = Alignment.CenterStart,
            painter = painterResource(id = R.drawable.ic_share_pdf),
            contentDescription = "share",
        )

    }

}
