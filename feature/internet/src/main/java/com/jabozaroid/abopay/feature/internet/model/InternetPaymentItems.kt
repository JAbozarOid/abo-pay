package com.jabozaroid.abopay.feature.internet.model

/**
 *  Created on 12/25/2024 
 **/
enum class InternetPaymentItems(var title: String) {

    CHARGE_AMOUNT("chargeAmount"),
    SERVICE_CATEGORY_CODE("ServiceCategoryCode"),
    TOPUP_TARGET_PHONE_NUMBER("topupTargetPhoneNumber"),
    PROVIDER_ID("providerId"),
    SERVICE_CODE("ServiceCode")

}