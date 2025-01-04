package com.jabozaroid.abopay.core.designsystem.component.model

data class IconItemUiModel(
    val index: Int = 0,
    val logo: String? = "",
    val lightLogo: String? = "",
    val darkLogo: String? = "",
    val title: String? = "",
    val model: Any? = null,//place of any type of model that you want use it
    val categoryCodesLst: List<String> = listOf(),
    val categories: List<CategoryItem> = listOf(),
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