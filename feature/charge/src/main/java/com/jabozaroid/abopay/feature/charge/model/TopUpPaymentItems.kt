package com.jabozaroid.abopay.feature.charge.model

/**
 *  Created on 12/25/2024
 **/
enum class TopUpPaymentItems(var title: String) {

    CHARGE_AMOUNT("chargeAmount"),
    SERVICE_CATEGORY_CODE("ServiceCategoryCode"),
    TOPUP_TARGET_PHONE_NUMBER("topupTargetPhoneNumber"),
    PROVIDER_ID("providerId"),
    SERVICE_CODE("ServiceCode")

}