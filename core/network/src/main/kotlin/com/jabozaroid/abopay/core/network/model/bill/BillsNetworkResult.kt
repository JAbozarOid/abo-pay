package com.jabozaroid.abopay.core.network.model.bill

data class BillsNetworkResult(
    val bills: List<BillNetworkResult>,
)

data class BillNetworkResult(
    val billName: String?,
    val billParameter: String?,
    val billType: Int?,
    val docUrl: String?,
    val payable: Boolean?,
    val isNotificationEnabled: Boolean?,
)