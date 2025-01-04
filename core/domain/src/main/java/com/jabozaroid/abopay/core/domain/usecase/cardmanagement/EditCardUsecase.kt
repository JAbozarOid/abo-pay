package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.EditSourceCardResult
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class EditCardUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<EditSourceCardParam, AboPayResult<EditSourceCardResult?>>() {
    override suspend fun onExecute(param: EditSourceCardParam): AboPayResult<EditSourceCardResult?> {
        return cardManagementRepository.editCard(param)
    }
}