package com.jabozaroid.abopay.core.network.model.bill

import com.google.gson.annotations.SerializedName

data class BillsTypeNetworkResult(
    @SerializedName("billTypes")
    val billTypes: List<BillTypeNetworkResult>,
)

data class BillTypeNetworkResult(
    val id: Int,
    val billTypeName: String?,
    val billTypeDesc: String?,
    val parameterName: String?,
    val logo: String?,
    val darkLogo: String?,
    val lightLogo: String?,
    val addToBillProfile: Boolean?,
    @SerializedName("spec")
    val specNetworkResult: SpecNetworkResult?,
    val canInquiry: Boolean?,
    val showNotification: Boolean?,
    val showBarcode: Boolean?,
    val parameterMaxLength: Int?,
    val addToRepeatTransaction: Boolean?,
    val wage: Int?,
    @SerializedName("availablePaymentMethods")
    val availablePaymentMethodNetworkResults: List<AvailablePaymentMethodNetworkResult>,
)

data class SpecNetworkResult(
    val type: Int?,
    val standardType: Int?,
    val validationType: Int?,
    val input: Int?,
)

data class AvailablePaymentMethodNetworkResult(
    val id: Int?,
    val title: String?,
    val titleFa: String?,
)