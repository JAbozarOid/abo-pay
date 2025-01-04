package com.jabozaroid.abopay.core.domain.model.cardmanagement.result

data class AddSourceCardResult(
    val responseCode: Int? = null,
    val responseMessage: String? = null,
    val status: Int? = null,
    val success: Boolean? = null,
    val token: String? = null,
)