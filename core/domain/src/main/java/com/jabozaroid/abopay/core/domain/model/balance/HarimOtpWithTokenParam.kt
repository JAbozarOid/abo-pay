package com.jabozaroid.abopay.core.domain.model.balance

data class HarimOtpWithTokenParam(

    val amount: Int? = null,

    val cardMedia: CardMediaToken? = CardMediaToken(),

    val requestType: Int? = null,

    )

data class CardMediaToken(

    val token: String? = null

)