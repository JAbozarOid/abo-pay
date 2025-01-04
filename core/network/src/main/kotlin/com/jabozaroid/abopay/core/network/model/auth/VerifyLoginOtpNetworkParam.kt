package com.jabozaroid.abopay.core.network.model.auth

/**
 *  Created on 8/21/2024 
 **/
data class VerifyLoginOtpNetworkParam (
    val Code: String,
    val UniqueTraceNumber: String,
    val Mobile: String
)
