package com.jabozaroid.abopay.core.domain.usecase.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithPanParam
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetCardBalanceWithPanUsecase @Inject constructor(private val balanceRepository: BalanceRepository) :
    BaseUseCase<CardBalanceWithPanParam, AboPayResult<BalanceResult?>>() {
    override suspend fun onExecute(param: CardBalanceWithPanParam): AboPayResult<BalanceResult?> =
        balanceRepository.getBalanceWithPan(param)
}