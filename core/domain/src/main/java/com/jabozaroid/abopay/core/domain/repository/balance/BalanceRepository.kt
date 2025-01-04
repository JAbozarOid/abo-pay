package com.jabozaroid.abopay.core.domain.repository.balance

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceVatResult
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.SourceCardListResult

interface BalanceRepository {

    suspend fun getOtpWithPan(param: HarimOtpWithPanParam): AboPayResult<HarimOtpResult?>

    suspend fun getBalanceWithPan(param: CardBalanceWithPanParam): AboPayResult<BalanceResult?>

    suspend fun getOtpWithToken(param: HarimOtpWithTokenParam): AboPayResult<HarimOtpResult?>

    suspend fun getBalanceWithToken(param: CardBalanceWithTokenParam): AboPayResult<BalanceResult?>

    suspend fun getBalanceVat(): AboPayResult<BalanceVatResult?>

    suspend fun getSourceCards(): AboPayResult<List<SourceCardListResult?>>
}