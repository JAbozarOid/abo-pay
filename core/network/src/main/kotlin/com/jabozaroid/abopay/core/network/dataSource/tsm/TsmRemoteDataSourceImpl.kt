package com.jabozaroid.abopay.core.network.dataSource.tsm

import com.jabozaroid.abopay.core.data.dataSource.tsm.TsmRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainParam
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResult
import com.jabozaroid.abopay.core.network.api.tsm.TsmApi
import com.jabozaroid.abopay.core.network.dataSource.tsm.mapper.mapToTsmDomainModel
import com.jabozaroid.abopay.core.network.dataSource.tsm.mapper.mapToTsmNetworkParam
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TsmRemoteDataSourceImpl @Inject constructor(
    private val tsmApi: TsmApi,
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
) : TsmRemoteDataSource {
    override suspend fun cardEnrollment(param: TsmDomainParam): AboPayResult<TsmDomainResult?> = execute {
        withContext(dispatcherProvider.io) {
            tsmApi.cardEnrollment(param = param.mapToTsmNetworkParam())
        }
    }.map {
        it.data?.mapToTsmDomainModel()
    }

    override suspend fun appReactivation(param: TsmDomainParam): AboPayResult<TsmDomainResult?> = execute{
        withContext(dispatcherProvider.io) {
            tsmApi.appReactivation(param = param.mapToTsmNetworkParam())
        }
    }.map {
        it.data?.mapToTsmDomainModel()
    }
}