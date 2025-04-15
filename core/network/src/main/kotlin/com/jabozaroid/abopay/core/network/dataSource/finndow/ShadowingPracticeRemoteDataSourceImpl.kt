package com.jabozaroid.abopay.core.network.dataSource.finndow

import com.jabozaroid.abopay.core.data.dataSource.finndow.ShadowingPracticeRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.finndow.param.ShadowingPracticeParam
import com.jabozaroid.abopay.core.domain.model.finndow.result.ShadowingPracticeResult
import com.jabozaroid.abopay.core.network.api.finndow.ShadowingPracticeApi
import com.jabozaroid.abopay.core.network.dataSource.finndow.mapper.mapToShadowingPracticeDomainModel
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShadowingPracticeRemoteDataSourceImpl @Inject constructor(
    private val shadowingPracticeApi: ShadowingPracticeApi,
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider
) : ShadowingPracticeRemoteDataSource {
    override suspend fun getShadowingPractice(param: ShadowingPracticeParam): AboPayResult<ShadowingPracticeResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                shadowingPracticeApi.getShadowingPractice(
                    param
                )
            }
        }.map {
            it.data?.mapToShadowingPracticeDomainModel()
        }

}