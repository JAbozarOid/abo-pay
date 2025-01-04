package com.jabozaroid.abopay.core.network.dataSource.home

import com.jabozaroid.abopay.core.data.dataSource.home.HomeServicesRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.home.HomeService
import com.jabozaroid.abopay.core.network.api.home.HomeServicesApi
import com.jabozaroid.abopay.core.network.dataSource.home.mapper.mapToDomainModel
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created on 31,August,2024
 */
class HomeServicesRemoteDataSourceImpl @Inject constructor(
    private val homeServicesApi: HomeServicesApi
) : HomeServicesRemoteDataSource {

    override suspend fun getHomeService(): AboPayResult<HomeService?> = execute {
        withContext(Dispatchers.IO) {
            homeServicesApi.getHomeService()
        }
    }.map {
        it.data?.mapToDomainModel()
    }

}