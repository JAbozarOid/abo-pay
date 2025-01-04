package com.jabozaroid.abopay.feature.internet.extention

import com.jabozaroid.abopay.core.designsystem.component.model.CategoryItem
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.ServiceItem
import com.jabozaroid.abopay.core.domain.model.internet.OperatorItem

internal fun List<OperatorItem>.toIconItemUiModel(): List<IconItemUiModel> {
    val mapList: MutableList<IconItemUiModel> = mutableListOf()
    for ((index, item) in this.withIndex()) {
        mapList.add(
            index,
            IconItemUiModel(
                lightLogo = item.logoUrl,
                darkLogo = item.logoUrl,
                title = item.name,
                index = item.index,
                categoryCodesLst = item.categoryCodesLst,
                categories = item.categories.map {
                    it.toCategoryItemUiModel()
                }
            )
        )
    }
    return mapList
}

internal fun com.jabozaroid.abopay.core.domain.model.internet.CategoryItem.toCategoryItemUiModel(): CategoryItem =
    CategoryItem(
        code = this.code,
        name = this.name,
        services = this.services.map { service ->
            ServiceItem(
                amount = service.amount,
                code = service.code,
                durationCode = service.durationCode,
                durationId = service.durationId,
                durationTitle = service.durationTitle,
                enabled = service.enabled,
                isFixedAmount = service.isFixedAmount,
                isWonderful = service.isWonderful,
                maxAmount = service.maxAmount,
                minAmount = service.minAmount,
                name = service.name,
                vat = service.vat,
            )
        }
    )