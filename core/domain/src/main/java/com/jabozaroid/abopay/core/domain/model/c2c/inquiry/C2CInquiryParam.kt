package com.jabozaroid.abopay.core.domain.model.c2c.inquiry

import com.google.gson.annotations.SerializedName

/**
 *  Created on 11/6/2024
 **/
data class C2CInquiryParam(
    val amount: String,
    val destinationPan: String?,
    val token: String?,
    val description: String?,
    val sourcePan: String?
)
