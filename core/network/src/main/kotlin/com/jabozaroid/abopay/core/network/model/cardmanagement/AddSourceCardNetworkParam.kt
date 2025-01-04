package com.jabozaroid.abopay.core.network.model.cardmanagement

data class AddSourceCardNetworkParam(
    val expireDate: String,
    val extraData: String,
//    val isDefault: Boolean,
    val primaryAccNo: String
)