package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteDestinationCardParam
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class DeleteDestinationCardUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<DeleteDestinationCardParam, AboPayResult<Boolean?>>() {
    override suspend fun onExecute(param: DeleteDestinationCardParam): AboPayResult<Boolean?> {
        return cardManagementRepository.deleteDestinationCard(param)
    }
}