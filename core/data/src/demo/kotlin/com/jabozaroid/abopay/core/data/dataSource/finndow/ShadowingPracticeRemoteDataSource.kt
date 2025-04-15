package com.jabozaroid.abopay.core.data.dataSource.finndow

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.finndow.param.ShadowingPracticeParam
import com.jabozaroid.abopay.core.domain.model.finndow.result.ShadowingPracticeResult

interface ShadowingPracticeRemoteDataSource {

    suspend fun getShadowingPractice(param: ShadowingPracticeParam): AboPayResult<ShadowingPracticeResult?>
}