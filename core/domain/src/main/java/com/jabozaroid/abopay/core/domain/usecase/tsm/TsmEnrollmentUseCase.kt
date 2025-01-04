package com.jabozaroid.abopay.core.domain.usecase.tsm

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainParam
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResult
import com.jabozaroid.abopay.core.domain.repository.tsm.TsmRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class TsmEnrollmentUseCase @Inject constructor(
    private val tsmRepository: TsmRepository,
) : BaseUseCase<TsmDomainParam, AboPayResult<TsmDomainResult?>>() {
    override suspend fun onExecute(param: TsmDomainParam): AboPayResult<TsmDomainResult?> {
        return tsmRepository.cardEnrollment(param = param)
    }
}