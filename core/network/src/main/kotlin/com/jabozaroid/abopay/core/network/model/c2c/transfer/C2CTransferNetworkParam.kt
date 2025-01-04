package com.jabozaroid.abopay.core.network.model.c2c.transfer

/**
 *  Created on 11/6/2024 
 **/
data class C2CTransferNetworkParam(
    val pin : String,
    val cvv2 :String,
    val expireDate : String,
    val inquiryToken : String

)
