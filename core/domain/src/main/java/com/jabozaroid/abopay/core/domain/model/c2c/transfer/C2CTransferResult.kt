package com.jabozaroid.abopay.core.domain.model.c2c.transfer

/**
 *  Created on 11/6/2024
 **/
data class C2CTransferResult(
    val trackingNumber : String = "",
    val amount: String="",
    val rrn: String="",
    val statusText: String="",
    val transactionDate: String="",
    val transactionId: String="",
    val registrationDate: Long = -1,
    val status: Int=-1,
    val stan: String="",
    val securityFactor :String="",
    val additionalResponseData : String="",
)
