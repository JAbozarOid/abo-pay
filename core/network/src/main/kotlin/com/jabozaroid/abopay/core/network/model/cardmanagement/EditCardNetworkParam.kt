package com.jabozaroid.abopay.core.network.model.cardmanagement

data class EditCardNetworkParam(
    val expireDate: String,
    val extraData: String,
    val isDefault: String,
    val token: String
)