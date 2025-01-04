package com.jabozaroid.abopay.core.network.model.auth

data class LoginOtpNetworkModel(
    val isInGracePeriod: String,
    val waitDuration: Float,
    val uniqueTraceNumber: String,
)