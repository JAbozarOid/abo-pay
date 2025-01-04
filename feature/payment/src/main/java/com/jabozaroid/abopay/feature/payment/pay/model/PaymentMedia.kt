package com.jabozaroid.abopay.feature.payment.pay.model

/**
 *  Created on 12/25/2024 
 **/
enum class PaymentMedia(val title: String) {

    CVV2("cvv2"),
    PAN("pan"),
    TOKEN("token"),
    PIN("pin"),
    EXPIRE_DATE("expireDate"),
    AMOUNT("amount")


}