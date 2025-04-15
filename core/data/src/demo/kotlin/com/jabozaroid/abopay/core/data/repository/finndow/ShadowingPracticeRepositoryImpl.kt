package com.jabozaroid.abopay.core.data.repository.finndow

import com.jabozaroid.abopay.core.data.dataSource.finndow.ShadowingPracticeRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.finndow.param.ShadowingPracticeParam
import com.jabozaroid.abopay.core.domain.model.finndow.result.ShadowingPracticeResult
import com.jabozaroid.abopay.core.domain.repository.finndow.ShadowingPracticeRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShadowingPracticeRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    private val shadowingPracticeRemoteDataSource: ShadowingPracticeRemoteDataSource
) : ShadowingPracticeRepository{
    override suspend fun getShadowingPractice(param: ShadowingPracticeParam): AboPayResult<ShadowingPracticeResult?> {
        return withContext(dispatcherProvider.io) {
            shadowingPracticeRemoteDataSource.getShadowingPractice(param)
        }
    }
}