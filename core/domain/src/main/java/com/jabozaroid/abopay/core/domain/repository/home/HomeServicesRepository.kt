package com.jabozaroid.abopay.core.domain.repository.home

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.home.HomeService

/**
 * Created on 31,August,2024
 */
interface HomeServicesRepository {

    suspend fun getHomeService(): AboPayResult<HomeService?>

}