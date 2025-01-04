package com.jabozaroid.abopay.core.domain.model.cardmanagement.result

data class MyCardsResult(
    val myCards: List<MyCardsResultItem> = listOf(),
)

data class MyCardsResultItem(
    val bankBin: String? = null,
    val bankNameFormat: String? = null,
    val extraData: String? = null,
    val hasExpDate: Boolean? = null,
    val hasTSM: Boolean? = null,
    val isDefault: Boolean? = null,
    val primaryAccNo: String? = null,
    val token: String? = null,
    val tokenExpDate: String? = null
)