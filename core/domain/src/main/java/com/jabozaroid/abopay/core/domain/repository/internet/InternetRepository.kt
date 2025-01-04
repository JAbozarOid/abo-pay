package com.jabozaroid.abopay.core.domain.repository.internet

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult

/**
 * Created on 16,October,2024
 */

interface InternetRepository {

    suspend fun getTopUpInternet(): AboPayResult<TopUpInternetResult?>


}