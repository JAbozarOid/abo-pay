package com.jabozaroid.abopay.core.data.repository.charge

import com.jabozaroid.abopay.core.data.dataSource.charge.ChargeRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.FavouriteChargeNumberResult
import com.jabozaroid.abopay.core.domain.model.charge.result.pin.PinChargeResult
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChargeRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    private val chargeRemoteDataSource: ChargeRemoteDataSource
) : ChargeRepository {
    override suspend fun getTopUpCharges(): AboPayResult<TopUpChargeResult?> {
        return withContext(dispatcherProvider.io) {
            chargeRemoteDataSource.getTopUpCharges()
        }
    }

    override suspend fun getPinCharges(): AboPayResult<PinChargeResult?> {
        return withContext(dispatcherProvider.io) { chargeRemoteDataSource.getPinCharges() }
    }

    override suspend fun getFavouriteChargeNumber(param: FavouriteChargeNumberParam): AboPayResult<FavouriteChargeNumberResult?> {
        return withContext(dispatcherProvider.io) {
            chargeRemoteDataSource.getFavouriteChargeNumber(
                param = param
            )
        }
    }

    override suspend fun deleteFavouriteChargeNumber(param: DeleteFavouriteChargeNumberParam): AboPayResult<Boolean> {
        return withContext(dispatcherProvider.io) {
            chargeRemoteDataSource.deleteFavouriteChargeNumber(
                param = param
            )
        }
    }
}