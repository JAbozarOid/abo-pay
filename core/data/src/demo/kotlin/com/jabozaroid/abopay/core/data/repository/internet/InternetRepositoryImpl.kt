package com.jabozaroid.abopay.core.data.repository.internet

import com.jabozaroid.abopay.core.data.dataSource.internet.InternetRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult
import com.jabozaroid.abopay.core.domain.repository.internet.InternetRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

/**
 * Created on 16,October,2024
 */

class InternetRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    @Named(com.jabozaroid.abopay.core.common.INTERNET_MOCK_DATASOURCE) private val internetRemoteDataSource: InternetRemoteDataSource,
) : InternetRepository {

    override suspend fun getTopUpInternet(): AboPayResult<TopUpInternetResult?> =
        withContext(dispatcherProvider.io) {
            internetRemoteDataSource.getTopUpInternet()
        }

}