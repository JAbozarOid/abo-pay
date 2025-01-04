package com.jabozaroid.abopay.feature.bill.preview

import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryItemResult
import com.jabozaroid.abopay.feature.bill.R
import com.jabozaroid.abopay.feature.bill.model.BillPaymentItems
import java.math.BigDecimal

/**
 * Created on 20,November,2024
 */

val billInquiriesResult: List<BillInquiryItemResult> = listOf(
    BillInquiryItemResult(
        amount = BigDecimal(705000.0),
        totalAmount = BigDecimal(705000.0),
        billId = "0057176411149",
        date = "1403/06/17",
        termType = 2,
        termTypeDesc = "پایان دوره",
        amountDescription = "705,000 ریال",
        payable = false,
        billType = 12,
        payId = "0000070530610",
        isChecked = true,
    ),
    BillInquiryItemResult(
        amount = BigDecimal(2245000.0),
        totalAmount = BigDecimal(12334235456),
        billId = "0057176411149",
        date = null,
        termType = 1,
        termTypeDesc = "میان دوره",
        amountDescription = "2,245,000 ریال",
        payable = false,
        billType = 12,
        payId = "0000224530767",
        isChecked = true,
    )
)

val frequentList: MutableList<FrequentUiModel> = mutableListOf(
    FrequentUiModel(
        id = 5556997,
        startIcon = AppIcons.icMoreBlack,
        endIcon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_color_resalat,
        overlayIcon1 = AppIcons.icRemove,
        overlayIcon2 = AppIcons.icEdit,
        firstText = "قبض برق",
        secondText = "1233345",
        overlayText1 = "حذف",
        overlayText2 = "ویرایش",
        endIconTint = null
    ),
    FrequentUiModel(
        id = 544656,
        startIcon = AppIcons.icMoreBlack,
        endIcon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_color_gardeshgari,
        overlayIcon1 = AppIcons.icRemove,
        overlayIcon2 = AppIcons.icEdit,
        firstText = "قبض آب",
        secondText = "4886",
        overlayText1 = "حذف",
        overlayText2 = "ویرایش",
        endIconTint = null
    ),
)


val dataModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
    commonItems = com.jabozaroid.abopay.core.common.model.CommonItems(
        paymentType = com.jabozaroid.abopay.core.common.model.PaymentCommonType.BILL,
        amount = "1,503,000",
        icon = R.drawable.ic_mci,
        iconTitle = "همراه اول",
        toolbarTitle = "پرداخت قبض"
    ),
    paymentItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            BillPaymentItems.PAY_PHONE_NUMBER.title,
            "09117883314"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            BillPaymentItems.BILL_ID.title,
            "4715963030154"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            BillPaymentItems.PAY_ID.title,
            "0000150336810"
        )

    ),
    visualItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شماره موبایل",
            "09117883314"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شناسه قبض",
            "4715963030154"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شناسه پرداخت",
            "0000150336810"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            " کارمزد (ریال)",
            "رایگان"
        )
    ),


    )