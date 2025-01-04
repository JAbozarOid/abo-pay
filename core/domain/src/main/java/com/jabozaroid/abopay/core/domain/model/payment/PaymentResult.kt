package com.jabozaroid.abopay.core.domain.model.payment

/**
 *  Created on 12/21/2024 
 **/

data class PaymentResult(
     val items: List<KeyValue>
)


data class KeyValue(
     val key: String,
     val value: String,
)
