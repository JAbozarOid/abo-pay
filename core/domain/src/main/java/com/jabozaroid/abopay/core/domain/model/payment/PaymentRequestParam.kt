package com.jabozaroid.abopay.core.domain.model.payment

/**
 *  Created on 12/24/2024 
 **/
data class PaymentRequestParam(
    val paymentType : Int,
    val items : List<KeyValue>
)

