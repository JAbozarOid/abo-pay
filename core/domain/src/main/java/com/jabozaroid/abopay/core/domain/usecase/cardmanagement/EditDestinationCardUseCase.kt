package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditDestinationCardParam
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class EditDestinationCardUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<EditDestinationCardParam, AboPayResult<Boolean?>>() {
    override suspend fun onExecute(param: EditDestinationCardParam): AboPayResult<Boolean?> {
        return cardManagementRepository.editDestinationCard(param)
    }
}