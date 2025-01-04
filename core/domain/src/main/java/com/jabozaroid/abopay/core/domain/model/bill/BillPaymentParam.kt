package com.jabozaroid.abopay.core.domain.model.bill

data class BillPaymentParam(
    val paymentId: String? = null,
    val billId: String? = null,
    val logo: String? = null,
    val amount: String? = null,
)