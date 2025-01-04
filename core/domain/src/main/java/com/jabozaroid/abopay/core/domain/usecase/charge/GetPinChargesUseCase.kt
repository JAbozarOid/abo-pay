package com.jabozaroid.abopay.core.domain.usecase.charge

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.result.pin.PinChargeResult
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetPinChargesUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository,

    ) : BaseUseCase<Unit, AboPayResult<PinChargeResult?>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<PinChargeResult?> {
        return chargeRepository.getPinCharges()
    }
}