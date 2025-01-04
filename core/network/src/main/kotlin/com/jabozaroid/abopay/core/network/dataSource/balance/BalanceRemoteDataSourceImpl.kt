package com.jabozaroid.abopay.core.network.dataSource.balance

import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.data.dataSource.balance.BalanceRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.balance.BalanceResult
import com.jabozaroid.abopay.core.domain.model.balance.BalanceVatResult
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpResult
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithPanParam
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithTokenParam
import com.jabozaroid.abopay.core.domain.model.balance.SourceCardListResult
import com.jabozaroid.abopay.core.domain.model.balance.SourceCardMonth
import com.jabozaroid.abopay.core.domain.model.balance.SourceCardYear
import com.jabozaroid.abopay.core.network.api.balance.BalanceApi
import com.jabozaroid.abopay.core.network.dataSource.balance.mapper.mapToDomainModel
import com.jabozaroid.abopay.core.network.dataSource.balance.mapper.mapToNetwork
import com.jabozaroid.abopay.core.network.dataSource.balance.mapper.mapToNetworkModel
import com.jabozaroid.abopay.core.network.helper.execute
import javax.inject.Inject

class BalanceRemoteDataSourceImpl @Inject constructor(
    private val balanceApi: BalanceApi
) : BalanceRemoteDataSource {
    override suspend fun getOtpWithPan(param: HarimOtpWithPanParam): AboPayResult<HarimOtpResult?> =
        execute {
            balanceApi.getHarimOtpWithPan(param.mapToNetworkModel())
        }.map {
            it.data?.mapToDomainModel()
        }

    override suspend fun getBalanceWithPin(param: CardBalanceWithPanParam): AboPayResult<BalanceResult?> =
        execute {
            balanceApi.getCardBalanceWithPan(param.mapToNetwork())
        }.map {
            it.data?.mapToDomainModel()
        }

    override suspend fun getOtpWithToken(param: HarimOtpWithTokenParam): AboPayResult<HarimOtpResult?> =
        execute {
            balanceApi.getHarimOtpWithToken(param.mapToNetworkModel())
        }.map {
            it.data?.mapToDomainModel()
        }

    override suspend fun getBalanceWithToken(param: CardBalanceWithTokenParam): AboPayResult<BalanceResult?> =
        execute {
            balanceApi.getCardBalanceWithToken(param.mapToNetwork())
        }.map {
            it.data?.mapToDomainModel()
        }

    override suspend fun getBalanceVat(): AboPayResult<BalanceVatResult?> =
        execute {
            balanceApi.getBalanceVat()
        }.map {
            it.data?.mapToDomainModel()
        }

    override suspend fun getSourceCards(): AboPayResult<List<SourceCardListResult?>> {

        //TODO: get list of card from server
        return AboPayResult.AboPaySuccess(
            listOf(
                SourceCardListResult(
                    ownerName = "ساناز آهنگ",
                    number = "6037997175607630",
                    token = "1",
                    month = SourceCardMonth(number = "09"),
                    year = SourceCardYear("1409"),
                    bankName = R.string.melli_bank,
                    colorUp = R.color.melli_up,
                    colorDown = R.color.melli_Down,
                    icon = R.drawable.card_icon_color_melli,

                    ),
                SourceCardListResult(
                    ownerName = "ساناز آهنگ",
                    number = "5892101502849876",
                    token = "2",
                    month = SourceCardMonth(number = "02"),
                    year = SourceCardYear("1406"),
                    bankName = R.string.sepah_bank,
                    colorUp = R.color.sepah_up,
                    colorDown = R.color.sepah_dwon,
                    icon = R.drawable.card_icon_color_sepah

                ),
                SourceCardListResult(
                    ownerName = "ساناز آهنگ",
                    number = "6104338921525206",
                    token = "3",
                    month = SourceCardMonth(number = "10"),
                    year = SourceCardYear("1403"),
                    bankName = R.string.mellat_bank,
                    colorUp = R.color.melat_up,
                    colorDown = R.color.melat_down,
                    icon = R.drawable.card_icon_color_mellat,
                    isDefault = true
                ),
                SourceCardListResult(
                    ownerName = "ساناز آهنگ",
                    number = "6037998284328142",
                    token = "4",
                    month = SourceCardMonth(number = "11"),
                    year = SourceCardYear("1490"),
                    bankName = R.string.melli_bank,
                    colorUp = R.color.melli_up,
                    colorDown = R.color.melli_Down,
                    icon = R.drawable.card_icon_color_melli

                )
            )
        )
    }


}