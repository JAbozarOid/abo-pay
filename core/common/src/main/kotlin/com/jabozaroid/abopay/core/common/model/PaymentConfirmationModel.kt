package com.jabozaroid.abopay.core.common.model

import com.jabozaroid.abopay.core.common.R


/**
 *  Created on 11/3/2024
 **/
data class PaymentConfirmationModel(
    var commonItems: com.jabozaroid.abopay.core.common.model.CommonItems,
    var visualItems: List<com.jabozaroid.abopay.core.common.model.MetaDataModel>,
    var paymentItems: List<com.jabozaroid.abopay.core.common.model.MetaDataModel>,
)


data class MetaDataModel(
    val key: String,
    val value: String,
)

data class CommonItems(
    val icon: Int? = R.drawable.card_icon_color_melli,
    val iconUrl: String = "",
    val iconTitle: String = "",
    val toolbarTitle: String = "",
    val vat: String = "",
    val payId: String = "",
    val paymentType: com.jabozaroid.abopay.core.common.model.PaymentCommonType = com.jabozaroid.abopay.core.common.model.PaymentCommonType.BUY,
    var amount: String = "",
)


