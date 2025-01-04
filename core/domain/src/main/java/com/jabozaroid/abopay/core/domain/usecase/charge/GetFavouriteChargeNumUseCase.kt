package com.jabozaroid.abopay.core.domain.usecase.charge

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.FavouriteChargeNumberResult
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetFavouriteChargeNumUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository,
) : BaseUseCase<FavouriteChargeNumberParam, AboPayResult<FavouriteChargeNumberResult?>>() {
    override suspend fun onExecute(param: FavouriteChargeNumberParam): AboPayResult<FavouriteChargeNumberResult?> {
        return chargeRepository.getFavouriteChargeNumber(param)
    }
}