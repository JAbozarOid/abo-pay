package com.jabozaroid.abopay.core.network.dataSource.cardmanagement

import com.jabozaroid.abopay.core.data.dataSource.cardmanagement.CardManagementRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.AddSourceCardResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DeleteSourceCardResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DestinationCardsResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.EditSourceCardResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.MyCardsResult
import com.jabozaroid.abopay.core.network.api.cardmanagement.CardManagementApi
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapAddCardResultToDomain
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapToAddCardParamData
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapToAddDestinationCardParamData
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapToDestinationCardsResultDomain
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapToEditCardParamData
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapToEditDestinationCardParamData
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper.mapToMyCardsResultDomain
import com.jabozaroid.abopay.core.network.helper.execute
import javax.inject.Inject

class CardManagementRemoteDataSourceImpl @Inject constructor(
    private val cardManagementApi: CardManagementApi,
) : CardManagementRemoteDataSource {
    override suspend fun getMyCards(): AboPayResult<MyCardsResult?> =
        execute {
            cardManagementApi.getSourceCards()
        }.map {
            it.data?.mapToMyCardsResultDomain()
        }

    override suspend fun deleteCard(param: DeleteSourceCardParam): AboPayResult<DeleteSourceCardResult?> =
        execute {
            cardManagementApi.deleteSourceCard(param)
        }.map {
            it.data?.mapToMyCardsResultDomain()
        }

    override suspend fun editCard(param: EditSourceCardParam): AboPayResult<EditSourceCardResult?> =
        execute {
            cardManagementApi.editSourceCard(param.mapToEditCardParamData())
        }.map {
            it.data?.mapToMyCardsResultDomain()
        }

    override suspend fun addCard(param: AddSourceCardParam): AboPayResult<AddSourceCardResult?> =
        execute {
            cardManagementApi.addSourceCard(param.mapToAddCardParamData())
        }.map {
            it.data?.mapAddCardResultToDomain()
        }

    override suspend fun getDestinationCards(): AboPayResult<DestinationCardsResult?> =
        execute {
            cardManagementApi.getDestinationCards()
        }.map {
            it.data?.mapToDestinationCardsResultDomain()
        }

    override suspend fun deleteDestinationCard(param: DeleteDestinationCardParam): AboPayResult<Boolean?> =
        execute {
            cardManagementApi.deleteDestinationCard(param.id)
        }.map {
            it.data
        }

    override suspend fun addDestinationCard(param: AddDestinationCardParam): AboPayResult<Boolean?> =
        execute {
            cardManagementApi.addDestinationCard(param.mapToAddDestinationCardParamData())
        }.map {
            it.data
        }

    override suspend fun editDestinationCard(param: EditDestinationCardParam): AboPayResult<Boolean?> =
        execute {
            cardManagementApi.editDestinationCard(param.mapToEditDestinationCardParamData())
        }.map {
            it.data
        }

}