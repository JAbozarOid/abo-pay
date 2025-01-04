package com.jabozaroid.abopay.core.domain.usecase.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithTokenParam
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetOtpWithTokenUsecase @Inject constructor(private val balanceRepository: BalanceRepository) :
    BaseUseCase<HarimOtpWithTokenParam, AboPayResult<HarimOtpResult?>>() {
    override suspend fun onExecute(param: HarimOtpWithTokenParam): AboPayResult<HarimOtpResult?> =
        balanceRepository.getOtpWithToken(param)
}