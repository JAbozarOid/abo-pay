package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DestinationCardsResult
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetDestinationCardsUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<Unit, AboPayResult<DestinationCardsResult?>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<DestinationCardsResult?> {
        return cardManagementRepository.getDestinationCards()
    }
}