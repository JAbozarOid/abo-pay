package com.jabozaroid.abopay.core.network.dataSource.charge.mapper

import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.FavouriteChargeNumberResult
import com.jabozaroid.abopay.core.domain.model.charge.result.pin.PinChargeResult
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.CategoryItem
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.OperatorItem
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.ServiceItem
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult
import com.jabozaroid.abopay.core.network.model.charge.param.DeleteFavouriteChargeNumberNetworkParam
import com.jabozaroid.abopay.core.network.model.charge.param.FavouriteChargeNumNetworkParam
import com.jabozaroid.abopay.core.network.model.charge.result.Category
import com.jabozaroid.abopay.core.network.model.charge.result.FavouriteChargeNumNetworkResult
import com.jabozaroid.abopay.core.network.model.charge.result.Item
import com.jabozaroid.abopay.core.network.model.charge.result.Operator
import com.jabozaroid.abopay.core.network.model.charge.result.PinChargeNetworkResult
import com.jabozaroid.abopay.core.network.model.charge.result.Service
import com.jabozaroid.abopay.core.network.model.charge.result.TopUpChargeNetworkResult

fun TopUpChargeNetworkResult.mapToTopUpChargeDomainModel(): TopUpChargeResult =
    TopUpChargeResult(
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
    color = this.color,
    darkLogoUrl = this.darkLogoUrl,
    lightLogoUrl = this.lightLogoUrl,
    mobileOperatorNameFa = this.mobileOperatorNameFa,
    name = this.name,
    wonderfulTitle = this.wonderfulTitle
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
    durationCode = durationCode,
    durationId = durationId,
    durationTitle = durationTitle,
    enabled = enabled,
    isFixedAmount = isFixedAmount,
    isWonderful = isWonderful,
    maxAmount = maxAmount,
    minAmount = minAmount,
    name = name,
    vat = vat
)

fun PinChargeNetworkResult.mapToPinChargeDomainModel(): PinChargeResult {
    return PinChargeResult().run { copy(catalogs = catalogs) }
}


fun FavouriteChargeNumberParam.mapToFavouriteChargeNumNetworkModel() =
    FavouriteChargeNumNetworkParam(
        currentPage = this.currentPage,
        recordPerPage = this.recordPerPage
    )

fun FavouriteChargeNumNetworkParam.toQueryMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    map["currentpage"] = this.currentPage
    map["recordperpage"] = this.recordPerPage

    return map
}

fun FavouriteChargeNumNetworkResult.mapToFavouriteChargeNumDomainModel(): FavouriteChargeNumberResult =
    FavouriteChargeNumberResult(
        items = this.favoriteMobileNumbers.map { item ->
            item.mapToFavoriteItemDomainModel()
        }
    )

fun Item.mapToFavoriteItemDomainModel() =
    com.jabozaroid.abopay.core.domain.model.charge.result.favourite.Item(
        phoneNumber = this.phoneNumber,
        ownerPhoneNumber = this.ownerPhoneNumber,
        icon = 0
    )

fun DeleteFavouriteChargeNumberNetworkParam.mapToFavouriteChargeNumDomainModel() =
    DeleteFavouriteChargeNumberParam(
        phoneNumber = this.phoneNumber
    )