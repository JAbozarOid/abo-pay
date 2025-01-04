package com.jabozaroid.abopay.core.domain.usecase.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithPanParam
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetOtpWithPanUsecase @Inject constructor(private val balanceRepository: BalanceRepository) :
    BaseUseCase<HarimOtpWithPanParam, AboPayResult<HarimOtpResult?>>() {
    override suspend fun onExecute(param: HarimOtpWithPanParam): AboPayResult<HarimOtpResult?> =
        balanceRepository.getOtpWithPan(param)
}