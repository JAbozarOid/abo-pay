package com.jabozaroid.abopay.core.domain.model.cardmanagement.param

data class AddSourceCardParam(
    val expireDate: String,
    val extraData: String,
//    val isDefault: Boolean,
    val primaryAccNo: String
)