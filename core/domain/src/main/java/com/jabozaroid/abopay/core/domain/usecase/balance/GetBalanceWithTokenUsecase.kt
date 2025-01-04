package com.jabozaroid.abopay.core.domain.usecase.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithTokenParam
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetBalanceWithTokenUsecase @Inject constructor(private val balanceRepository: BalanceRepository) :
    BaseUseCase<CardBalanceWithTokenParam, AboPayResult<BalanceResult?>>() {
    override suspend fun onExecute(param: CardBalanceWithTokenParam): AboPayResult<BalanceResult?> =
        balanceRepository.getBalanceWithToken(param)
}