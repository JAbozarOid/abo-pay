package com.jabozaroid.abopay.core.domain.model.cardmanagement.param

data class EditSourceCardParam(
    val expireDate: String,
    val extraData: String,
    val isDefault: Boolean,
    val token: String
)