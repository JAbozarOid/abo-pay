package com.jabozaroid.abopay.core.domain.usecase.finndow

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.finndow.param.ShadowingPracticeParam
import com.jabozaroid.abopay.core.domain.model.finndow.result.ShadowingPracticeResult
import com.jabozaroid.abopay.core.domain.repository.finndow.ShadowingPracticeRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetShadowingPracticeUseCase @Inject constructor(
    private val shadowingPracticeRepository: ShadowingPracticeRepository,
) : BaseUseCase<ShadowingPracticeParam, AboPayResult<ShadowingPracticeResult?>>(){
    override suspend fun onExecute(param: ShadowingPracticeParam): AboPayResult<ShadowingPracticeResult?> {
        return shadowingPracticeRepository.getShadowingPractice(param)
    }
}