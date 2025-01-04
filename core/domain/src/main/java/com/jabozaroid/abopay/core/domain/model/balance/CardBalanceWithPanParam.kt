package com.jabozaroid.abopay.core.domain.model.balance

data class CardBalanceWithPanParam(
    var cvv2: String? = null,

    var expireDate: String? = null,

    var pin: String? = null,

    var pan: String? = null

)
