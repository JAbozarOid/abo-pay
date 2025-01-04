package com.jabozaroid.abopay.core.network.model.payment

/**
 *  Created on 12/24/2024 
 **/


data class PaymentNetworkParam(
    val paymentType: Int,
    val items: List<NetworkKeyValue>
)