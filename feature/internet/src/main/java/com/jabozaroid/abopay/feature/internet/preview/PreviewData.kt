package com.jabozaroid.abopay.feature.internet.preview

import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.feature.internet.R
import com.jabozaroid.abopay.feature.internet.model.InternetPaymentItems

/**
 * Created on 20,November,2024
 */

val dataModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
    commonItems = com.jabozaroid.abopay.core.common.model.CommonItems(
        paymentType = com.jabozaroid.abopay.core.common.model.PaymentCommonType.INTERNET,
        amount = "30000",
        icon = R.drawable.ic_irancell,
        iconTitle = "ایرانسل",
        toolbarTitle = "بسته اینترنت",
        vat = "15"
    ),
    paymentItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            InternetPaymentItems.CHARGE_AMOUNT.title,
            "123test"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            InternetPaymentItems.SERVICE_CATEGORY_CODE.title,
            "123test"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            InternetPaymentItems.TOPUP_TARGET_PHONE_NUMBER.title,
            "123test"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            InternetPaymentItems.PROVIDER_ID.title,
            "123test"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            InternetPaymentItems.SERVICE_CODE.title,
            "1"
        )
    ),
    visualItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "نوع بسته",
            "بسته یک ماهه 4 گیگابایت"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شَماره سیم کارت",
            "09117883314"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "مبلغ شارژ(ریال)",
            "2130,000"
        )
    )
)

val frequentList: MutableList<FrequentUiModel> = mutableListOf(
    FrequentUiModel(
        startIcon = AppIcons.icMoreBlack,
        endIcon = R.drawable.ic_mci,
        overlayIcon1 = AppIcons.icRemove,
        overlayIcon2 = AppIcons.icRetry,
        firstText = "بسته یک روزه 250 مگابایت",
        secondText = "09104757572",
        thirdText = "۹۲.۰۰۰ ریال - با احتساب ۱۰ درصد مالیات",
        overlayText1 = "حذف",
        overlayText2 = "انجام مجدد",
        endIconTint = null,
        isOverlayViewVisibility = true
    ),
    FrequentUiModel(
        startIcon = AppIcons.icMoreBlack,
        endIcon = R.drawable.ic_rightel,
        overlayIcon1 = AppIcons.icRemove,
        overlayIcon2 = AppIcons.icRetry,
        firstText = "بسته ی یک هفته - ۴ گیگ - ۲ تا ۷ صبح",
        secondText = "092175988667",
        thirdText = "۲۴۰.۰۰۰ ریال - با احتساب ۱۰ درصد مالیات",
        overlayText1 = "حذف",
        overlayText2 = "انجام مجدد",
        endIconTint = null,
        isOverlayViewVisibility = true
    ),
)