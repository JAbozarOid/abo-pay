package com.jabozaroid.abopay.core.domain

data class AboPayServerError(
    val error: ErrorData = ErrorData(),
)

data class ErrorData(
    val code: Int? = -1,
    val message: String? = "",
)