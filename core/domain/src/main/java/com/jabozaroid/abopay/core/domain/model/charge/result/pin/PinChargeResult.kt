package com.jabozaroid.abopay.core.domain.model.charge.result.pin

data class PinChargeResult(
    val catalogs: List<CatalogItem> = listOf()
)

data class CatalogItem(
    val chargeProviderName: String,
    val chargeProviderCode: String,
    val catalogAmount: List<Double>
)
