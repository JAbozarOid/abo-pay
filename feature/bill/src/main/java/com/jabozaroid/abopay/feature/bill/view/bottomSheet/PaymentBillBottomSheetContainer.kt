package com.jabozaroid.abopay.feature.bill.view.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.UnsafeImageApp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.domain.model.bill.BillPaymentParam


@Composable
internal fun PaymentBillBottomSheet(
    title: String,
    paymentParam: BillPaymentParam,
    onDismiss: () -> Unit,
    onPaymentButton: () -> Unit,
    onCancelButton: () -> Unit,
) {
    BottomSheetComponent(
        title = title, content = {
            PaymentBillBottomSheetContainer(
                modifier = Modifier.padding(
                    Dimens.size_16,
                ),
                billId = paymentParam.billId ?: "",
                payId = paymentParam.paymentId ?: "",
                amount = paymentParam.amount ?: "",
                logo = paymentParam.logo ?: ""
            )
        },
        onDismiss = onDismiss,
        btn1Text = aboPayStringResource(R.string.pay),
        btn2Text = aboPayStringResource(R.string.cancel),
        onBtn1Click = onPaymentButton,
        onBtn2Click = onCancelButton
    )
}

@Composable
internal fun PaymentBillBottomSheetContainer(
    modifier: Modifier,
    billId: String,
    payId: String,
    amount: String,
    logo:String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topEnd = Dimens.size_12,
                    topStart = Dimens.size_12
                )
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topEnd = Dimens.size_12,
                        topStart = Dimens.size_12
                    )
                )
        ) {
            val (
                iconRef,
                descriptionRef, priceBoxRef,
            ) = createRefs()
            if (logo.isNotEmpty()) {
                UnsafeImageApp(
                    darkLogo = logo,
                    lightLogo = logo,
                    placeholder = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_feature,
                    modifier = Modifier
                        .constrainAs(iconRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.start)
                            width = Dimension.matchParent
                        }
                        .size(42.dp),
                    contentDescription = "",
                )
            }
            Column(modifier = Modifier
                .constrainAs(descriptionRef) {
                    top.linkTo(iconRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Row(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.size_24,
                            vertical = Dimens.size_8
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                        Text(
                            text = billId,
                            color = AppTheme.colorScheme.aboTitleText
                        )
                    }
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(
                            text = aboPayStringResource(R.string.bill_id),
                            color = AppTheme.colorScheme.aboTitleText
                        )
                    }
                }

                Row(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.size_24,
                            vertical = Dimens.size_8
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                        Text(
                            text = payId,
                            color = AppTheme.colorScheme.aboTitleText
                        )
                    }
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(
                            text = aboPayStringResource(R.string.payment_id),
                            color = AppTheme.colorScheme.aboTitleText
                        )
                    }
                }
            }


            Price(
                modifier = Modifier
                    .constrainAs(priceBoxRef) {
                        top.linkTo(descriptionRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.matchParent
                    }, amount = amount
            )


        }
    }
}

@Composable
internal fun Price(modifier: Modifier, amount: String) {
    Column(modifier = modifier.wrapContentSize()) {
        HorizontalDivider(
            modifier = Modifier
                .padding(Dimens.size_15)
                .background(AppTheme.colorScheme.background2)
                .height(Dimens.size_2)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = Dimens.size_15),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                Text(
                    text = amount,
                    color = AppTheme.colorScheme.aboTitleText
                )
            }
            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                Text(
                    text = aboPayStringResource(R.string.amount_rial),
                    color = AppTheme.colorScheme.aboTitleText
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(Dimens.size_15)
                .background(AppTheme.colorScheme.background2)
                .height(Dimens.size_2)
        )
    }
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
internal fun PreviewPriceBox() {
    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        PaymentBillBottomSheetContainer(
            Modifier,
            billId = "4146804930158",
            payId = "000013889054",
            amount = "1.380.890",
            logo = ""
        )
    }
}
