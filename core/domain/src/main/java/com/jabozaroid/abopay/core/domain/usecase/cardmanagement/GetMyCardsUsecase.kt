package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.MyCardsResult
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetMyCardsUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<Unit, AboPayResult<MyCardsResult?>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<MyCardsResult?> {
        return cardManagementRepository.getMyCards()
    }
}