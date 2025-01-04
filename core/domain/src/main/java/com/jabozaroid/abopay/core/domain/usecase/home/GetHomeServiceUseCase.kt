package com.jabozaroid.abopay.core.domain.usecase.home

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.home.HomeService
import com.jabozaroid.abopay.core.domain.repository.home.HomeServicesRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

/**
 * Created on 31,August,2024
 */

class GetHomeServiceUseCase @Inject constructor(
    private val homeServicesRepository: HomeServicesRepository,
) : BaseUseCase<Unit, AboPayResult<HomeService?>>() {

    override suspend fun onExecute(param: Unit): AboPayResult<HomeService?> =
        homeServicesRepository.getHomeService()

}