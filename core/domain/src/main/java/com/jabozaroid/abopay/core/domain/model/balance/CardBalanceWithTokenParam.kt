package com.jabozaroid.abopay.core.domain.model.balance

data class CardBalanceWithTokenParam(
    val cvv2: String? = null,

    val ExpireDate: String? = null,

    val pin: String? = null,

    val token: String? = null

)
