package com.jabozaroid.abopay.core.network.model.payment

/**
 *  Created on 12/21/2024
 **/
data class PaymentNetworkResult(
     val items: List<NetworkKeyValue>
)


data class NetworkKeyValue(
     val key: String,
     val value: String,
)
