package com.jabozaroid.abopay.core.domain.model.balance

data class BalanceResult(
    val availableAmountSeparated: String? = null,

    val amountSeparated: String? = null,

    val availableAmount: String? = null,

    val amount: String? = null,

    val maskedPan: String? = null,

    val primaryAccNo: String? = null,

    val issuerName: String? = null,

    val transactionDate: String? = null,

    val trace: String? = null,

    val rrn: String? = null,

    val point: Int? = null

)