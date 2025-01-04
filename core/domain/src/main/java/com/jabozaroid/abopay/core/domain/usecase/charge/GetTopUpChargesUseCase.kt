package com.jabozaroid.abopay.core.domain.usecase.charge

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetTopUpChargesUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository,
) : BaseUseCase<Unit?, AboPayResult<TopUpChargeResult?>>() {
    override suspend fun onExecute(param: Unit?): AboPayResult<TopUpChargeResult?> {
        return chargeRepository.getTopUpCharges()
    }
}