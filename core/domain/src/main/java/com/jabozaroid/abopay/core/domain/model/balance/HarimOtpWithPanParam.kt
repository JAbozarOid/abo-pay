package com.jabozaroid.abopay.core.domain.model.balance

data class HarimOtpWithPanParam(
    val amount: Int? = null,

    val cardMediaPan: CardMediaPan? = CardMediaPan(),

    val requestType: Int? = null
)

data class CardMediaPan(
    val pan: String? = null
)