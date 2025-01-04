package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DeleteSourceCardResult
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<DeleteSourceCardParam, AboPayResult<DeleteSourceCardResult?>>() {
    override suspend fun onExecute(param: DeleteSourceCardParam): AboPayResult<DeleteSourceCardResult?> {
        return cardManagementRepository.deleteCard(param)
    }
}