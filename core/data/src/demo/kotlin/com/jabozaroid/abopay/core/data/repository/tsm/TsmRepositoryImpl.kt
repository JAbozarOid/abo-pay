package com.jabozaroid.abopay.core.data.repository.tsm

import com.jabozaroid.abopay.core.data.dataSource.tsm.TsmRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainParam
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResult
import com.jabozaroid.abopay.core.domain.repository.tsm.TsmRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TsmRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    private val tsmRemoteDataSource: TsmRemoteDataSource,
) : TsmRepository {
    override suspend fun cardEnrollment(param: TsmDomainParam): AboPayResult<TsmDomainResult?> {
        return withContext(dispatcherProvider.io) {
            tsmRemoteDataSource.cardEnrollment(param = param)
        }
    }

    override suspend fun appReactivation(param: TsmDomainParam): AboPayResult<TsmDomainResult?> {
        return withContext(dispatcherProvider.io) {
            tsmRemoteDataSource.cardEnrollment(param = param)
        }
    }
}