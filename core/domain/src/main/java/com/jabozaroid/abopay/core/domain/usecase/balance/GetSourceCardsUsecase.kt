package com.jabozaroid.abopay.core.domain.usecase.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.SourceCardListResult
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetSourceCardsUsecase @Inject constructor(private val balanceRepository: BalanceRepository) :
    BaseUseCase<Unit, AboPayResult<List<SourceCardListResult?>>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<List<SourceCardListResult?>> =
        balanceRepository.getSourceCards()
}


