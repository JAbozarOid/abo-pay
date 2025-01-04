package com.jabozaroid.abopay.core.data.repository.cardmanagement

import com.jabozaroid.abopay.core.data.dataSource.cardmanagement.CardManagementRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
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
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardManagementRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    private val cardManagementRemoteDataSource: CardManagementRemoteDataSource,
) : CardManagementRepository {

    override suspend fun getMyCards(): AboPayResult<MyCardsResult?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.getMyCards()
        }
    }

    override suspend fun deleteCard(param: DeleteSourceCardParam): AboPayResult<DeleteSourceCardResult?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.deleteCard(param)
        }
    }

    override suspend fun editCard(param: EditSourceCardParam): AboPayResult<EditSourceCardResult?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.editCard(param)
        }
    }

    override suspend fun addCard(param: AddSourceCardParam): AboPayResult<AddSourceCardResult?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.addCard(param)
        }
    }

    override suspend fun getDestinationCards(): AboPayResult<DestinationCardsResult?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.getDestinationCards()
        }
    }

    override suspend fun deleteDestinationCard(param: DeleteDestinationCardParam): AboPayResult<Boolean?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.deleteDestinationCard(param)
        }
    }

    override suspend fun addDestinationCard(param: AddDestinationCardParam): AboPayResult<Boolean?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.addDestinationCard(param)
        }
    }

    override suspend fun editDestinationCard(param: EditDestinationCardParam): AboPayResult<Boolean?> {
        return withContext(dispatcherProvider.io) {
            cardManagementRemoteDataSource.editDestinationCard(param)
        }
    }
}