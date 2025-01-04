package com.jabozaroid.abopay.core.domain.usecase.internet

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult
import com.jabozaroid.abopay.core.domain.repository.internet.InternetRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

/**
 * Created on 16,October,2024
 */
class GetTopUpInternetUseCase @Inject constructor(
    private val internetRepository: InternetRepository,
) : BaseUseCase<Unit?, AboPayResult<TopUpInternetResult?>>() {
    override suspend fun onExecute(param: Unit?): AboPayResult<TopUpInternetResult?> {
        return internetRepository.getTopUpInternet()
    }
}