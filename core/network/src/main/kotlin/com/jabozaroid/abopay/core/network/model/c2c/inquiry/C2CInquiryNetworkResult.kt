package com.jabozaroid.abopay.core.network.model.c2c.inquiry

/**
 *  Created on 11/9/2024 
 **/
data class C2CInquiryNetworkResult(
    val cardHolderInquiry : CardHolderNetworkInquiry
)


data class CardHolderNetworkInquiry(
    val fullName : String,
    val inquiryToken : String,
    val isEnvelopRequired : Boolean
)