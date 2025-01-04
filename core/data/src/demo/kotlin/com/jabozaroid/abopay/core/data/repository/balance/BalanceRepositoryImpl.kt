package com.jabozaroid.abopay.core.data.repository.balance

import com.jabozaroid.abopay.core.data.dataSource.balance.BalanceRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceVatResult
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.SourceCardListResult
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import javax.inject.Inject
import javax.inject.Named

class BalanceRepositoryImpl @Inject constructor(
    @Named(com.jabozaroid.abopay.core.common.BALANCE_REMOTE_DATASOURCE)
    private val balanceRemoteDataSource: BalanceRemoteDataSource,
) : BalanceRepository {
    override suspend fun getOtpWithPan(param: HarimOtpWithPanParam): AboPayResult<HarimOtpResult?> =
        balanceRemoteDataSource.getOtpWithPan(param)


    override suspend fun getBalanceWithPan(param: CardBalanceWithPanParam): AboPayResult<BalanceResult?> =
        balanceRemoteDataSource.getBalanceWithPin(param)


    override suspend fun getOtpWithToken(param: HarimOtpWithTokenParam): AboPayResult<HarimOtpResult?> =
        balanceRemoteDataSource.getOtpWithToken(param)


    override suspend fun getBalanceWithToken(param: CardBalanceWithTokenParam): AboPayResult<BalanceResult?> =
        balanceRemoteDataSource.getBalanceWithToken(param)

    override suspend fun getBalanceVat(): AboPayResult<BalanceVatResult?> =
        balanceRemoteDataSource.getBalanceVat()

    override suspend fun getSourceCards(): AboPayResult<List<SourceCardListResult?>> =
        balanceRemoteDataSource.getSourceCards()


}