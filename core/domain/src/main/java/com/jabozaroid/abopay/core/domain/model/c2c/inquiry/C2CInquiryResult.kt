package com.jabozaroid.abopay.core.domain.model.c2c.inquiry

/**
 *  Created on 11/9/2024 
 **/
data class C2CInquiryResult(
    val cardHolderInquiry : CardHolderInquiry
)


data class CardHolderInquiry(
    val fullName : String,
    val inquiryToken : String,
    val isEnvelopRequired : Boolean
)