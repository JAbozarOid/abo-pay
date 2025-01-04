package com.jabozaroid.abopay.core.domain.model.tsm

data class TsmDomainResult(
    val tsmResponse: TsmDomainResponse = TsmDomainResponse(),
)

data class TsmDomainResponse(
    val trackingNumber: String = "",
    val transactionId: String = "",
    val registrationDate: Long = 0L,
    val registrationAddress: String = "",

    )