package com.jabozaroid.abopay.core.domain.usecase.charge

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class DeleteFavoriteMobileNumberUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository,
) : BaseUseCase<DeleteFavouriteChargeNumberParam, AboPayResult<Boolean>>() {
    override suspend fun onExecute(param: DeleteFavouriteChargeNumberParam): AboPayResult<Boolean> {
        return chargeRepository.deleteFavouriteChargeNumber(param)
    }
}