package com.jabozaroid.abopay.core.network.model.charge.result

data class PinChargeNetworkResult(
    val catalogs: List<CatalogItem>
)

data class CatalogItem(
    val chargeProviderName: String,
    val chargeProviderCode: String,
    val catalogAmount: List<Double>
)
