package com.jabozaroid.abopay.core.network.dataSource.internet

import com.jabozaroid.abopay.core.data.dataSource.internet.InternetRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult
import com.jabozaroid.abopay.core.network.api.internet.InternetApi
import com.jabozaroid.abopay.core.network.dataSource.internet.mapper.mapToTopUpInternetDomainModel
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created on 16,October,2024
 */

class InternetRemoteDataSourceImpl @Inject constructor(
    private val internetApi: InternetApi,
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider
) : InternetRemoteDataSource {

    override suspend fun getTopUpInternet(): AboPayResult<TopUpInternetResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                internetApi.getTopUpCatalogInternet()
            }
        }.map {
            it.data?.mapToTopUpInternetDomainModel()
        }

}


