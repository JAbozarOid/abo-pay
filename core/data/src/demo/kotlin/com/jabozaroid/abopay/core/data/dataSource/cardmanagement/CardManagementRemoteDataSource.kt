package com.jabozaroid.abopay.core.data.dataSource.cardmanagement

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

interface CardManagementRemoteDataSource {

    suspend fun getMyCards(): AboPayResult<MyCardsResult?>

    suspend fun deleteCard(param: DeleteSourceCardParam): AboPayResult<DeleteSourceCardResult?>

    suspend fun editCard(param: EditSourceCardParam): AboPayResult<EditSourceCardResult?>

    suspend fun addCard(param: AddSourceCardParam): AboPayResult<AddSourceCardResult?>

    suspend fun getDestinationCards(): AboPayResult<DestinationCardsResult?>

    suspend fun deleteDestinationCard(param: DeleteDestinationCardParam): AboPayResult<Boolean?>

    suspend fun addDestinationCard(param: AddDestinationCardParam): AboPayResult<Boolean?>

    suspend fun editDestinationCard(param: EditDestinationCardParam): AboPayResult<Boolean?>

}