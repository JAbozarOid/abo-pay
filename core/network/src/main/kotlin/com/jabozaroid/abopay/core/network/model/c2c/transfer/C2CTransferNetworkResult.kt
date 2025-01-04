package com.jabozaroid.abopay.core.network.model.c2c.transfer

/**
 *  Created on 11/6/2024 
 **/
data class C2CTransferNetworkResult(
    val trackingNumber : String,
    val amount: String,
    val destinationPan: String,
    val rrn: String,
    val statusText: String,
    val transactionDate: String,
    val transactionId: String,
    val registrationDate: Long,
    val status: Int,
    val stan: String,
    val securityFactor :String,
    val additionalResponseData : String
)
