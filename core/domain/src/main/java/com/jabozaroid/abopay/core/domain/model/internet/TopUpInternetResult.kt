package com.jabozaroid.abopay.core.domain.model.internet

/**
 * Created on 16,October,2024
 */

data class TopUpInternetResult(
    val isActiveTransportation: Boolean,
    val operators: List<OperatorItem>
)

data class OperatorItem(
    val index: Int = 0,
    val agreement: String,
    val categories: List<CategoryItem>,
    val categoryCodesLst: List<String>,
    val code: Int,
    val logoUrl: String,
    val mobileOperatorNameFa: String,
    val name: String
)

data class CategoryItem(
    val code: Int,
    val name: String,
    val services: List<ServiceItem>
)

data class ServiceItem(
    val amount: Double,
    val code: Int,
    val durationCode: String,
    val durationId: Int,
    val durationTitle: String,
    val enabled: Boolean,
    val isFixedAmount: Boolean,
    val isWonderful: Boolean,
    val maxAmount: Double,
    val minAmount: Double,
    val name: String,
    val vat: Double
)

