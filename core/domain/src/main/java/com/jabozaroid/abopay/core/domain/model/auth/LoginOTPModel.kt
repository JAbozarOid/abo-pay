package com.jabozaroid.abopay.core.domain.model.auth

data class LoginOTPModel(
    val isInGracePeriod: String?,
    val waitDuration: Float?,
    val uniqueTraceNumber: String?,
)