package com.jabozaroid.abopay.core.network.dataSource.charge

import com.jabozaroid.abopay.core.data.dataSource.charge.ChargeRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.FavouriteChargeNumberResult
import com.jabozaroid.abopay.core.domain.model.charge.result.pin.PinChargeResult
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult
import com.jabozaroid.abopay.core.network.api.charge.ChargeApi
import com.jabozaroid.abopay.core.network.dataSource.charge.mapper.mapToFavouriteChargeNumDomainModel
import com.jabozaroid.abopay.core.network.dataSource.charge.mapper.mapToFavouriteChargeNumNetworkModel
import com.jabozaroid.abopay.core.network.dataSource.charge.mapper.mapToPinChargeDomainModel
import com.jabozaroid.abopay.core.network.dataSource.charge.mapper.mapToTopUpChargeDomainModel
import com.jabozaroid.abopay.core.network.dataSource.charge.mapper.toQueryMap
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChargeRemoteDataSourceImpl @Inject constructor(
    private val chargeApi: ChargeApi,
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider
) : ChargeRemoteDataSource {
    override suspend fun getTopUpCharges(): AboPayResult<TopUpChargeResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                chargeApi.getTopUpCharges()
            }
        }.map {
            it.data?.mapToTopUpChargeDomainModel()
        }

    override suspend fun getPinCharges(): AboPayResult<PinChargeResult?> = execute {
        withContext(dispatcherProvider.io) {
            chargeApi.getPinCharges()
        }
    }.map {
        it.data?.mapToPinChargeDomainModel()
    }

    override suspend fun getFavouriteChargeNumber(param: FavouriteChargeNumberParam): AboPayResult<FavouriteChargeNumberResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                chargeApi.getFavouriteChargeNumbers(
                    param.mapToFavouriteChargeNumNetworkModel().toQueryMap()
                )
            }
        }.map {
            it.data?.mapToFavouriteChargeNumDomainModel()
        }

    override suspend fun deleteFavouriteChargeNumber(param: DeleteFavouriteChargeNumberParam): AboPayResult<Boolean> =
        execute {
            withContext(dispatcherProvider.io) {
                chargeApi.deleteFavouriteChargeNumbers(
                    param.phoneNumber
                )
            }
        }.map {
            it.data ?: false
        }

}