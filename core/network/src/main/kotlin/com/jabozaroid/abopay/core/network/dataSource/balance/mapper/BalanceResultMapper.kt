package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.network.model.balance.BalanceNetworkResult

fun BalanceNetworkResult.mapToDomainModel() =
    BalanceResult(
        availableAmountSeparated = availableAmountSeparated,
        amount = amount,
        rrn = rrn,
        maskedPan = maskedPan,
        point = point,
        trace = trace,
        amountSeparated = amountSeparated,
        availableAmount = availableAmount,
        transactionDate = transactionDate,
        primaryAccNo = primaryAccNo,
        issuerName = issuerName
    )