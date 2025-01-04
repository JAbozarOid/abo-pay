package com.jabozaroid.abopay.core.network.model.charge.result

data class TopUpChargeNetworkResult(
    val isActiveTransportation: Boolean?,
    val operators: List<Operator>
)

data class Operator(
    val categories: List<Category>,
    val categoryCodesLst: List<String>,
    val code: Int?,
    val color: String?,
    val darkLogoUrl: String?,
    val lightLogoUrl: String?,
    val mobileOperatorNameFa: String?,
    val name: String?,
    val wonderfulTitle: String?
)

data class Category(
    val code: Int?,
    val name: String?,
    val services: List<Service>
)

data class Service(
    val amount: Double?,
    val code: Int?,
    val durationCode: String?,
    val durationId: Int?,
    val durationTitle: String?,
    val enabled: Boolean?,
    val isFixedAmount: Boolean?,
    val isWonderful: Boolean?,
    val maxAmount: Any?,
    val minAmount: Any?,
    val name: String?,
    val vat: Double?
)