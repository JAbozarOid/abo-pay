package com.jabozaroid.abopay.core.data.dataSource.internet

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult

/**
 * Created on 16,October,2024
 */

interface InternetRemoteDataSource {

    suspend fun getTopUpInternet(): AboPayResult<TopUpInternetResult?>

}