package com.jabozaroid.abopay.core.domain.usecase.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceVatResult
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetBalanceVatUsecase @Inject constructor(private val balanceRepository: BalanceRepository) :
    BaseUseCase<Unit, AboPayResult<BalanceVatResult?>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<BalanceVatResult?> =
        balanceRepository.getBalanceVat()
}