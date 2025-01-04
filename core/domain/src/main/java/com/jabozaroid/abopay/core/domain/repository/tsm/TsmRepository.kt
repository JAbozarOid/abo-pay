package com.jabozaroid.abopay.core.domain.repository.tsm

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainParam
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResult

interface TsmRepository {

    suspend fun cardEnrollment(param: TsmDomainParam) : AboPayResult<TsmDomainResult?>
    suspend fun appReactivation(param: TsmDomainParam) : AboPayResult<TsmDomainResult?>
}