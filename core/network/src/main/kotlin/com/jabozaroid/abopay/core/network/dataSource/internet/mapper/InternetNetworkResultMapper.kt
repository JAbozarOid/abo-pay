package com.jabozaroid.abopay.core.network.dataSource.internet.mapper

import com.jabozaroid.abopay.core.domain.model.internet.CategoryItem
import com.jabozaroid.abopay.core.domain.model.internet.OperatorItem
import com.jabozaroid.abopay.core.domain.model.internet.ServiceItem
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult
import com.jabozaroid.abopay.core.network.model.internet.Category
import com.jabozaroid.abopay.core.network.model.internet.Operator
import com.jabozaroid.abopay.core.network.model.internet.Service
import com.jabozaroid.abopay.core.network.model.internet.TopUpInternetNetworkResult

/**
 * Created on 16,October,2024
 */

fun TopUpInternetNetworkResult.mapToTopUpInternetDomainModel(): TopUpInternetResult =
    TopUpInternetResult(
        operators = this.operators.map { operator ->
            operator.mapToOperatorDomainModel()
        },
        isActiveTransportation = this.isActiveTransportation
    )

fun Operator.mapToOperatorDomainModel() = OperatorItem(
    categories = this.categories.map { category ->
        category.mapToOperatorDomainModel()
    },
    categoryCodesLst = this.categoryCodesLst,
    code = this.code,
    logoUrl = this.logoUrl ?: "",
    mobileOperatorNameFa = this.mobileOperatorNameFa,
    name = this.name,
    agreement = this.agreement ?: ""
)

fun Category.mapToOperatorDomainModel() = CategoryItem(
    code = this.code,
    name = this.name,
    services = this.services.map { service ->
        service.mapToOperatorDomainModel()
    }
)

fun Service.mapToOperatorDomainModel() = ServiceItem(
    amount = amount,
    code = code,
    durationCode = durationCode ?: "400",
    durationId = durationId,
    durationTitle = durationTitle ?: "سایر",
    enabled = enabled,
    isFixedAmount = isFixedAmount,
    isWonderful = isWonderful,
    maxAmount = maxAmount,
    minAmount = minAmount,
    name = name,
    vat = vat
)