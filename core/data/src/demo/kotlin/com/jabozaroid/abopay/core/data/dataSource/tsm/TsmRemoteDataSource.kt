package com.jabozaroid.abopay.core.data.dataSource.tsm

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainParam
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResult

interface TsmRemoteDataSource {
    suspend fun cardEnrollment(param: TsmDomainParam): AboPayResult<TsmDomainResult?>
    suspend fun appReactivation(param: TsmDomainParam): AboPayResult<TsmDomainResult?>
}