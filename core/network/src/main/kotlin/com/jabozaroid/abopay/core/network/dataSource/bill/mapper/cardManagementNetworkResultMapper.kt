package com.jabozaroid.abopay.core.network.dataSource.bill.mapper

import com.jabozaroid.abopay.core.domain.model.bill.BillResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsResult
import com.jabozaroid.abopay.core.network.model.bill.BillNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillsNetworkResult

fun BillsNetworkResult.mapToBillsResultDomain() =
    BillsResult(
        billNetworkResults = bills.map {
            it.mapToBillResultDomain()
        }
    )

fun BillNetworkResult.mapToBillResultDomain() =
    BillResult(
        billName = billName,
        billParameter = billParameter,
        billType = billType,
        docUrl = docUrl,
        payable = payable,
        isNotificationEnabled = isNotificationEnabled,
    )

