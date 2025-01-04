package com.jabozaroid.abopay.core.domain.usecase.cardmanagement

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.AddSourceCardResult
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardManagementRepository: CardManagementRepository) :
    BaseUseCase<AddSourceCardParam, AboPayResult<AddSourceCardResult?>>() {
    override suspend fun onExecute(param: AddSourceCardParam): AboPayResult<AddSourceCardResult?> {
        return cardManagementRepository.addCard(param)
    }
}