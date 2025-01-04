package com.jabozaroid.abopay.core.domain.model.bill


data class BillsTypeResult(
    val billTypes: List<BillTypeResult>,
)

data class BillTypeResult(
    val index: Int = 0,
    val id: Int?,
    val billTypeName: String?,
    val billTypeDesc: String?,
    val parameterName: String?,
    val logo: String?,
    val darkLogo: String?,
    val lightLogo: String?,
    val addToBillProfile: Boolean?,
    val specResult: SpecResult?,
    val canInquiry: Boolean?,
    val showNotification: Boolean?,
    val showBarcode: Boolean?,
    val parameterMaxLength: Int?,
    val addToRepeatTransaction: Boolean?,
    val wage: Int?,
    val availablePaymentMethodResults: List<AvailablePaymentMethodResult>,
)

data class SpecResult(
    val type: Int?,
    val standardType: Int?,
    val validationType: Int?,
    val input: Int?,
)

data class AvailablePaymentMethodResult(
    val id: Int?,
    val title: String?,
    val titleFa: String?,
)