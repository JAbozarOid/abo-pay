package com.jabozaroid.abopay.core.data.repository.home

import com.jabozaroid.abopay.core.data.dataSource.home.HomeServicesRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.home.HomeService
import com.jabozaroid.abopay.core.domain.repository.home.HomeServicesRepository
import javax.inject.Inject
import javax.inject.Named

/**
 * Created on 31,August,2024
 */

class HomeServicesRepositoryImpl @Inject constructor(
    @Named(com.jabozaroid.abopay.core.common.HOME_SERVICE_MOCK_DATASOURCE) private val homeServicesRemoteDataSource: HomeServicesRemoteDataSource,
) : HomeServicesRepository {

    override suspend fun getHomeService(): AboPayResult<HomeService?> =
        homeServicesRemoteDataSource.getHomeService()

}