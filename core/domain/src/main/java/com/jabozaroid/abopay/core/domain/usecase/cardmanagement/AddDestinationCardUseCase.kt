package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddDestinationCardParam
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class AddDestinationCardUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<AddDestinationCardParam, AboPayResult<Boolean?>>() {
    override suspend fun onExecute(param: AddDestinationCardParam): AboPayResult<Boolean?> {
        return cardManagementRepository.addDestinationCard(param)
    }
}