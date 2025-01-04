package com.jabozaroid.abopay.core.data.dataSource.charge

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.FavouriteChargeNumberResult
import com.jabozaroid.abopay.core.domain.model.charge.result.pin.PinChargeResult
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult

interface ChargeRemoteDataSource {

    suspend fun getTopUpCharges(): AboPayResult<TopUpChargeResult?>

    suspend fun getPinCharges(): AboPayResult<PinChargeResult?>

    suspend fun getFavouriteChargeNumber(param: FavouriteChargeNumberParam): AboPayResult<FavouriteChargeNumberResult?>

    suspend fun deleteFavouriteChargeNumber(param: DeleteFavouriteChargeNumberParam): AboPayResult<Boolean>
}