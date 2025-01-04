package com.jabozaroid.abopay.core.data.dataSource.home

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.home.HomeService

/**
 * Created on 31,August,2024
 */
interface HomeServicesRemoteDataSource {

    suspend fun getHomeService(): AboPayResult<HomeService?>

}