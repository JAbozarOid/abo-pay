package com.jabozaroid.abopay.core.common.model

data class CardCommonModel(
    val cardNumber: String,
    val cardIconColor: Int?,
    val cardIconWhite: Int?,
    val errorMessage: Int?,
    val bankName : Int?,
    val cardColorUp : Int?,
    val cardColorDown : Int?
)
