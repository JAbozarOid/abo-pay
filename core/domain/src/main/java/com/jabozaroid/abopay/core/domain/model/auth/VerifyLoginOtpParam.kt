package com.jabozaroid.abopay.core.domain.model.auth

data class VerifyLoginOtpParam(
    val Code: String,
    val UniqueTraceNumber: String,
    val Mobile: String
)