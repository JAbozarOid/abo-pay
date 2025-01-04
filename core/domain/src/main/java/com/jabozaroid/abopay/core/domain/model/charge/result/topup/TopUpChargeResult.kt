package com.jabozaroid.abopay.core.domain.model.charge.result.topup

data class TopUpChargeResult(
    val isActiveTransportation: Boolean?,
    val operators: List<OperatorItem>
)

data class OperatorItem(
    var index : Int = 0,
    val categories: List<CategoryItem>,
    val categoryCodesLst: List<String>,
    val code: Int?,
    val color: String?,
    val darkLogoUrl: String?,
    val lightLogoUrl: String?,
    val mobileOperatorNameFa: String?,
    val name: String?,
    val wonderfulTitle: String?
)

data class CategoryItem(
    val code: Int?,
    val name: String?,
    val services: List<ServiceItem>
)

data class ServiceItem(
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