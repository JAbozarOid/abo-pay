package com.jabozaroid.abopay.core.network.dataSource.internet

import com.jabozaroid.abopay.core.data.dataSource.internet.InternetRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult
import javax.inject.Inject

/**
 * Created on 31,August,2024
 */

class InternetMockDataSourceImpl @Inject constructor() : InternetRemoteDataSource {

    override suspend fun getTopUpInternet(): AboPayResult<TopUpInternetResult?> =
        AboPayResult.AboPaySuccess(
            mockedInternetTopUpCatalogs
        )
}