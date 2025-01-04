package com.jabozaroid.abopay.feature.bill.view.bottomSheet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.UnsafeImageApp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryItemResult
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.feature.bill.R
import com.jabozaroid.abopay.feature.bill.preview.billInquiriesResult

@Composable
internal fun InquiryBillBottomSheet(
    onDismiss: () -> Unit,
    title: String,
    logo: String,
    onPayment: () -> Unit,
    onCancel: () -> Unit,
    btn1Text: String = "",
    btn2Text: String = "",
    model: BillInquiryResult,
    telephone: String?,
    phoneNumber: String?,
) {

    var descriptionList: List<BillInquiryItemResult?> = remember {
        listOf(BillInquiryItemResult())
    }
    var selectedDescription = remember {
        model.billInquiryList?.get(0)
    }
    model.billInquiryList?.let {
        if (it.isNotEmpty()) {
            descriptionList = it
        }

    }
    BottomSheetComponent(
        title = title,
        content =
        {
            val isTwoTypePayment =
                if (!model.billInquiryList.isNullOrEmpty()) model.billInquiryList!!.size > 1
                else false

            BillInquiryContent(
                modifier = Modifier,
                icon = logo,
                description = descriptionList,
                isTwoTypePayment = isTwoTypePayment,
                selectedDescription = selectedDescription,
                onBtnMidTermClick = {
                    selectedDescription = it
                },
                onBtnFullTermClick = {
                    selectedDescription = it
                },
                wage = model.wage,
                phoneNumber = phoneNumber,
                telephone = telephone
            )
        },
        onDismiss = onDismiss,
        onBtn1Click = onPayment,
        onBtn2Click = onCancel,
        btn1Text = btn1Text,
        btn2Text = btn2Text
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
internal fun BillInquiryContent(
    modifier: Modifier,
    icon: String,
    description: List<BillInquiryItemResult?>,
    selectedDescription: BillInquiryItemResult?,
    isTwoTypePayment: Boolean,
    onBtnMidTermClick: (BillInquiryItemResult) -> Unit,
    onBtnFullTermClick: (BillInquiryItemResult) -> Unit,
    wage: String?,
    phoneNumber: String?,
    telephone: String?,
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
                descriptionContentRef, priceBoxRef,
            ) = createRefs()
            var hasIcon = false
            if (icon.isNotEmpty()) {
                hasIcon = true
                UnsafeImageApp(
                    modifier = Modifier
                        .size(Dimens.size_40, Dimens.size_40)
                        .constrainAs(iconRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.start)
                            width = Dimension.matchParent
                        },
                    darkLogo = icon,
                    lightLogo = icon,
                    placeholder = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_feature,
                    contentDescription = "Item"
                )

            }
            DescriptionContent(
                item = selectedDescription,
                wage = wage,
                telephone = telephone,
                phoneNumber = phoneNumber,
                isTwoTypePayment = isTwoTypePayment,
                modifier = Modifier
                    .constrainAs(descriptionContentRef) {
                        top.linkTo(if (hasIcon) iconRef.bottom else parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.start)
                        width = Dimension.matchParent
                    }
                    .padding(top = Dimens.size_34)
                    .fillMaxWidth())
            if (isTwoTypePayment)
                TwoTypePriceBox(
                    modifier = Modifier
                        .constrainAs(priceBoxRef) {
                            top.linkTo(descriptionContentRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.matchParent
                        },
                    data = description,
                    onBtnMidTermClick = onBtnMidTermClick,
                    onBtnFullTermClick = onBtnFullTermClick
                )
            else
                PriceBox(
                    modifier = Modifier
                        .constrainAs(priceBoxRef) {
                            top.linkTo(descriptionContentRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.matchParent
                        }, data = description[0]//data.value, data.key)
                )
        }

    }
}

@Composable
internal fun DescriptionContent(
    item: BillInquiryItemResult?, modifier: Modifier,
    wage: String?,
    phoneNumber: String?,
    telephone: String?,
    isTwoTypePayment: Boolean,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        //region BillId
        if (!item?.billId.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = item?.billId ?: "",
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bill_id),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion
        //region PayId
        if (!item?.payId.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = item?.payId.toString(),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.payment_id),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion
        //region wage
        if (!wage.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = if (wage == "0") aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.free) else "",
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bill_wage),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion

        //region Amount
        if (isTwoTypePayment && !item?.amountDescription.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = item?.amountDescription ?: "",
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.payable),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion

        //region PhoneNumber
        if (!phoneNumber.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = phoneNumber,
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.mobile_number),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion

        //region Telephone
        if (!telephone.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = telephone,
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.telephone_number),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion

        //region Date for pay
        if (!item?.date.isNullOrEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.size_15,
                        vertical = Dimens.size_2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = item?.date ?: "",
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                    Text(
                        text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.payment_deadline),
                        color = AppTheme.colorScheme.ivaTitleText
                    )
                }
            }
        //endregion
    }
}


@Composable
internal fun PriceBox(modifier: Modifier, data: BillInquiryItemResult?) {
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
                    text = data?.amountDescription ?: "",
                    color = AppTheme.colorScheme.ivaTitleText
                )
            }
            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                Text(
                    text = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.amount),
                    color = AppTheme.colorScheme.ivaTitleText
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

@SuppressLint("ResourceType")
@Composable
internal fun TwoTypePriceBox(
    modifier: Modifier, data: List<BillInquiryItemResult?>,
    onBtnMidTermClick: (BillInquiryItemResult) -> Unit,
    onBtnFullTermClick: (BillInquiryItemResult) -> Unit,
) {
    var termType = remember {
        data[1]?.termType
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.size_8),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val boxLeftSelected = rememberSaveable { mutableStateOf(true) }
        //region Box Left
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onBtnFullTermClick(data[0] ?: BillInquiryItemResult())
                    data[0]?.termType?.let {
                        termType = it
                    }
                    boxLeftSelected.value = false
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = AppTheme.colorScheme.ivaWhiteBackground,
                    shape = RoundedCornerShape(Dimens.size_8)
                )

                .padding(vertical = Dimens.size_8)
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_gray),
                colorFilter = ColorFilter.tint(if (!boxLeftSelected.value) AppTheme.colorScheme.ivaSwitchSelected else AppTheme.colorScheme.ivaDisableButtonBackground),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(Dimens.size_8)
                    .align(Alignment.Center)
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_10PX_13SP_M) {
                    Text(
                        text = data[0]?.termTypeDesc ?: "",
                        color = AppTheme.colorScheme.ivaTitleText,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = data[0]?.amountDescription ?: "",
                        color = AppTheme.colorScheme.ivaTitleText,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
        //endregion
        //region Box Right
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onBtnMidTermClick(data[1] ?: BillInquiryItemResult())
                    data[1]?.termType?.let {
                        termType = it
                    }
                    boxLeftSelected.value = true
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = AppTheme.colorScheme.ivaWhiteBackground,
                    shape = RoundedCornerShape(Dimens.size_8)
                )
                .padding(vertical = Dimens.size_8))
        {
            Image(
                painter = painterResource(id = R.drawable.rectangle_yellow),
                colorFilter = ColorFilter.tint(if (boxLeftSelected.value) AppTheme.colorScheme.ivaSwitchSelected else AppTheme.colorScheme.ivaDisableButtonBackground),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(Dimens.size_8)
                    .align(Alignment.Center)
            ) {
                ProvideTextStyle(value = AppTheme.typography.text_10PX_13SP_M) {
                    Text(
                        text = data[1]?.termTypeDesc
                            ?: aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.middle_period),
                        color = AppTheme.colorScheme.ivaTitleText,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_B) {
                    Text(
                        text = data[1]?.amountDescription
                            ?: aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.final_period),
                        color = AppTheme.colorScheme.ivaTitleText,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        //endregion

    }
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
//@DevicePreviews
internal fun PreviewBottomSheetExample() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topEnd = Dimens.size_12,
                        topStart = Dimens.size_12
                    )
                )
        ) {
            BillInquiryContent(
                modifier = Modifier.padding(Dimens.size_8),
                icon = "",
                description = billInquiriesResult,
                isTwoTypePayment = billInquiriesResult.size > 1,
                onBtnMidTermClick = {},
                onBtnFullTermClick = {},
                selectedDescription = billInquiriesResult[0],
                wage = "0",
                phoneNumber = "09199780876",
                telephone = "02177466306"
            )
        }
    }
}
