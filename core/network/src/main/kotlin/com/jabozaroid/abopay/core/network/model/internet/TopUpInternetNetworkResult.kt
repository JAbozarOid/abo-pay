package com.jabozaroid.abopay.core.network.model.internet

/**
 * Created on 16,October,2024
 */

data class TopUpInternetNetworkResult(
    val isActiveTransportation: Boolean,
    val operators: List<Operator>
)

data class Operator(
    val agreement: String?,
    val categories: List<Category>,
    val categoryCodesLst: List<String>,
    val code: Int,
    val logoUrl: String?,
    val mobileOperatorNameFa: String,
    val name: String
)

data class Category(
    val code: Int,
    val name: String,
    val services: List<Service>
)

data class Service(
    val amount: Double,
    val code: Int,
    val durationCode: String?,
    val durationId: Int,
    val durationTitle: String?,
    val enabled: Boolean,
    val isFixedAmount: Boolean,
    val isWonderful: Boolean,
    val maxAmount: Double,
    val minAmount: Double,
    val name: String,
    val vat: Double
)

