package com.jabozaroid.abopay.core.domain.model.c2c.transfer

/**
 *  Created on 11/6/2024 
 **/
data class C2CTransferParam(
    val pin : String,
    val cvv2 :String,
    val expireDate : String,
    val inquiryToken : String
)
