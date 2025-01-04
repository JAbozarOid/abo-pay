package com.jabozaroid.abopay.core.domain.model.bill

data class BillsResult(
    val billNetworkResults: List<BillResult>,
)

data class BillResult(
    val billName: String?,
    val billParameter: String?,
    val billType: Int?,
    val docUrl: String?,
    val payable: Boolean?,
    val isNotificationEnabled: Boolean?,
)