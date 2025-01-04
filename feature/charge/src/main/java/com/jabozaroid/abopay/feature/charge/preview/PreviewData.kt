package com.jabozaroid.abopay.feature.charge.preview

import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.feature.charge.R
import com.jabozaroid.abopay.feature.charge.model.TopUpPaymentItems

/**
 * Created on 20,November,2024
 */

val dataModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
    commonItems = com.jabozaroid.abopay.core.common.model.CommonItems(
        paymentType = com.jabozaroid.abopay.core.common.model.PaymentCommonType.CHARGE,
        amount = "56,000",
        icon = R.drawable.ic_irancell,
        iconTitle = "شارژ مستقیم",
        toolbarTitle = "خرید شارژ",
        vat = "10"
    ),
    paymentItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            TopUpPaymentItems.CHARGE_AMOUNT.title,
            "123test"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            TopUpPaymentItems.SERVICE_CATEGORY_CODE.title,
            "123test"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            TopUpPaymentItems.TOPUP_TARGET_PHONE_NUMBER.title,
            "123test"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            TopUpPaymentItems.PROVIDER_ID.title,
            "123test"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            TopUpPaymentItems.SERVICE_CODE.title,
            "1"
        )
    ),
    visualItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "نوع شارژ",
            "شارژ مستقیم"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شَماره سیم کارت",
            "09359776687"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "مبلغ شارژ(ریال)",
            "50.000"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شناسه پرداخت",
            "000986343565"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            " کارمزد (ریال)",
            "رایگان"
        )
    )
)

val frequentList: MutableList<FrequentUiModel> = mutableListOf(
    FrequentUiModel(
        startIcon = AppIcons.icMoreBlack,
        endIcon = R.drawable.ic_mci,
        overlayIcon1 = AppIcons.icRemove,
        overlayIcon2 = AppIcons.icRetry,
        firstText = "شارژ مستقیم - 92.000 ریال",
        secondText = "09104757572",
        overlayText1 = "حذف",
        overlayText2 = "انجام مجدد",
        endIconTint = null
    ),
    FrequentUiModel(
        startIcon = AppIcons.icMoreBlack,
        endIcon = R.drawable.ic_rightel,
        overlayIcon1 = AppIcons.icRemove,
        overlayIcon2 = AppIcons.icRetry,
        firstText = "شارژ مستقیم - 56.000 ریال",
        secondText = "092175988667",
        overlayText1 = "حذف",
        overlayText2 = "انجام مجدد",
        endIconTint = null
    ),
)