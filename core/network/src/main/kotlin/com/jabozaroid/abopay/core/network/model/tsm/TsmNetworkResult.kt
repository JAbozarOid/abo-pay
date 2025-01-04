package com.jabozaroid.abopay.core.network.model.tsm

data class TsmNetworkResult(
    val tsmResponse: TsmResponse,
)

data class TsmResponse(
    val trackingNumber: String,
    val transactionId: String,
    val registrationDate: Long,
    val registrationAddress: String,
)
