package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.network.model.balance.HarimOtpNetworkResult

fun HarimOtpNetworkResult.mapToDomainModel() =
    HarimOtpResult(
        correlationId = correlationId,
        responseCode = responseCode,
        transactionDateTime = transactionDateTime,
        systemTrace = systemTrace,
        message = message

    )