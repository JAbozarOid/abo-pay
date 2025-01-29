package com.jabozaroid.abopay.feature.payment.paymentConfirmation.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.AppPrimaryButton
import com.jabozaroid.abopay.core.designsystem.component.DottedShape
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.model.PaymentConfirmationUiModel


@Composable
internal fun ButtonContent(
    modifier: Modifier,
    onCancelButtonClicked: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
) {
    Row(modifier = modifier, Arrangement.SpaceBetween) {
        AppOutlinedButton(
            onClick = {
                onCancelButtonClicked()
            },
            text = {
                Text(
                    text = aboPayStringResource(id = R.string.cancel),
                    style = AppTheme.typography.text_11PX_15SP_B
                )
            }, modifier = Modifier
                .weight(0.45f)
                .padding(horizontal = Dimens.size_8)
        )
        AppPrimaryButton(
            modifier = Modifier.weight(0.75f),
            onClick = {
                onConfirmButtonClicked()
            },

            text = aboPayStringResource(R.string.confirm)
        )
    }

}


@Composable
internal fun TitleContent(modifier: Modifier, state: PaymentConfirmationUiModel) {

    Column(modifier = modifier.padding(all = Dimens.size_8), Arrangement.Center) {
        ProvideTextStyle(
            value = AppTheme.typography.text_14PX_19SP_M.copy(
                fontWeight = FontWeight.Bold,
                textDirection = TextDirection.ContentOrRtl
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = Dimens.size_15),
                text = aboPayStringResource(R.string.payment_confirm),
                style = AppTheme.typography.text_12PX_16SP_M.copy(
                    fontWeight = FontWeight.W700
                ),
                color = AppTheme.colorScheme.aboTitleText
            )
        }

        ImageContent(
            state,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
                .align(alignment = Alignment.CenterHorizontally),
        )


        Spacer(modifier = Modifier.padding(Dimens.size_4))

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = state.paymentConfirmationModel.commonItems.iconTitle,
            style = AppTheme.typography.text_10PX_13SP_M.copy(
                fontWeight = FontWeight.W700
            ),
            color = AppTheme.colorScheme.aboTitleText
        )

    }

}


@Composable
internal fun ImageContent(state: PaymentConfirmationUiModel, modifier: Modifier) {

   val  painter = if (state.paymentConfirmationModel.commonItems.iconUrl.isNotBlank()) {

        rememberAsyncImagePainter(
            model = state.paymentConfirmationModel.commonItems.iconUrl,
            placeholder = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_abo_pay)
        )
    } else painterResource(id = state.paymentConfirmationModel.commonItems.icon!!)
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = " icon"
    )
}


@Composable
internal fun MetaDataContainer(modifier: Modifier, state: PaymentConfirmationUiModel) {

    Column(
        modifier = modifier
            .padding(all = Dimens.size_8)
    ) {
        Box(
            Modifier
                .height(Dimens.size_1)
                .fillMaxWidth()
                .background(
                    color = AppTheme.colorScheme.aboTitleText,
                    shape = DottedShape(step = Dimens.size_10)
                )
        )


        LazyColumn(
            modifier = Modifier
                .padding(all = Dimens.size_8)
                .wrapContentSize()
                .defaultMinSize(minHeight = 100.dp)

        ) {
            items(count = state.paymentConfirmationModel.visualItems.size) { item ->
                KeyValueRow(item = state.paymentConfirmationModel.visualItems[item])
            }
        }
        Box(
            Modifier
                .height(Dimens.size_1)
                .fillMaxWidth()
                .background(
                    color = AppTheme.colorScheme.aboTitleText,
                    shape = DottedShape(step = Dimens.size_10)
                )
        )
        PriceContainer(
            state = state,
            modifier = Modifier
                .padding(vertical = Dimens.size_8)
                .fillMaxWidth(),
            price = state.amountWithVat
        )
        Box(
            Modifier
                .height(Dimens.size_1)
                .fillMaxWidth()
                .background(
                    color = AppTheme.colorScheme.aboTitleText,
                    shape = DottedShape(step = Dimens.size_10)
                )
        )

    }

}


@Composable
internal fun KeyValueRow(item: com.jabozaroid.abopay.core.common.model.MetaDataModel) {

    Row(
        Modifier.fillMaxWidth(), Arrangement.SpaceBetween
    )
    {
        Text(
            style = AppTheme.typography.text_10PX_13SP_M.copy(
                fontWeight = FontWeight.Bold

            ),
            text = item.value,
            modifier = Modifier.padding(all = Dimens.size_5),
            color = AppTheme.colorScheme.aboTitleText
        )

        Text(
            text = item.key,
            style = AppTheme.typography.text_10PX_13SP_M,
            modifier = Modifier.padding(all = Dimens.size_5),
            color = AppTheme.colorScheme.aboTitleText
        )

    }


}

@Composable
internal fun PriceContainer(modifier: Modifier, price: String, state: PaymentConfirmationUiModel) {

    val visualTransformation = remember {
        com.jabozaroid.abopay.core.common.util.CurrencyAmountInputVisualTransformation()
    }
    val transformedText = remember(price) {
        visualTransformation.filter(
            AnnotatedString(price)
        )
    }.text
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = Dimens.size_8, start = Dimens.size_8,
                    top = Dimens.size_10
                ), Arrangement.SpaceBetween
        )
        {

            Text(
                style = AppTheme.typography.text_12PX_16SP_M.copy(
                    fontWeight = FontWeight.Bold
                ),
                text = transformedText,
                modifier = Modifier.padding(all = Dimens.size_5),
                color = AppTheme.colorScheme.aboTitleText
            )



            Text(
                style = AppTheme.typography.text_12PX_16SP_M.copy(
                    fontWeight = FontWeight.Bold
                ),
                text = aboPayStringResource(id = R.string.payable_rial),
                modifier = Modifier.padding(all = Dimens.size_5),
                color = AppTheme.colorScheme.aboTitleText
            )


        }
        if (state.paymentConfirmationModel.commonItems.vat.isNotBlank()) {
            Text(
                style = AppTheme.typography.text_9PX_12SP_M,
                text = aboPayStringResource(
                    R.string.vat_title,
                    state.paymentConfirmationModel.commonItems.vat
                ),
                modifier = Modifier
                    .padding(
                        end = Dimens.size_15, start = Dimens.size_15,
                        bottom = Dimens.size_8
                    )
                    .align(alignment = Alignment.End),
                color = AppTheme.colorScheme.aboTitleText
            )
        }
    }
}


@Preview
@Composable
fun ShowMetaDataContainer() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            MetaDataContainer(modifier = Modifier, state = PaymentConfirmationUiModel())
        }
    }
}