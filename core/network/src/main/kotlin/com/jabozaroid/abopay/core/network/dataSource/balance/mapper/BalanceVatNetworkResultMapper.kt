package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.BalanceVatResult
import com.jabozaroid.abopay.core.network.model.balance.BalanceVatNetworkResult

fun BalanceVatNetworkResult.mapToDomainModel() = BalanceVatResult(vat = vat)
