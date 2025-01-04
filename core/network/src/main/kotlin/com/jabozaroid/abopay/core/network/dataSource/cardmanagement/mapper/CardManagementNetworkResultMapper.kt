package com.jabozaroid.abopay.core.network.dataSource.cardmanagement.mapper

import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.AddSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditDestinationCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.EditSourceCardParam
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.AddSourceCardResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DeleteSourceCardResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DestinationCard
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DestinationCardsResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.EditSourceCardResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.MyCardsResult
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.MyCardsResultItem
import com.jabozaroid.abopay.core.network.model.cardmanagement.AddDestinationCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.AddSourceCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.AddSourceCardNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.DeleteSourceCardNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.DestinationCardNetwork
import com.jabozaroid.abopay.core.network.model.cardmanagement.DestinationCardsNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.EditCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.EditDestinationCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.EditSourceCardNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.MyCardsNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.MyCardsNetworkResultItem

fun MyCardsNetworkResult.mapToMyCardsResultDomain() =
    MyCardsResult(
        myCards = myCards.map {
            it.mapToMyCardsResultItemDomain()
        }
    )

fun DeleteSourceCardNetworkResult.mapToMyCardsResultDomain() =
    DeleteSourceCardResult(
        success = success,
    )

fun EditSourceCardNetworkResult.mapToMyCardsResultDomain() =
    EditSourceCardResult(
        success = success,
    )

fun MyCardsNetworkResultItem.mapToMyCardsResultItemDomain() =
    MyCardsResultItem(
        bankBin = bankBin,
        extraData = extraData,
        hasExpDate = hasExpDate,
        hasTSM = hasTSM,
        isDefault = isDefault,
        primaryAccNo = primaryAccNo,
        token = token,
        tokenExpDate = tokenExpDate,
        bankNameFormat = bankNameFormat
    )

fun EditSourceCardParam.mapToEditCardParamData(): EditCardNetworkParam =
    EditCardNetworkParam(
        expireDate = expireDate,
        extraData = extraData,
        isDefault = isDefault.toString(),
        token = token
    )

fun AddSourceCardParam.mapToAddCardParamData(): AddSourceCardNetworkParam =
    AddSourceCardNetworkParam(
        expireDate = expireDate,
        extraData = extraData,
//        isDefault = isDefault,
        primaryAccNo = primaryAccNo
    )

fun AddSourceCardNetworkResult.mapAddCardResultToDomain(): AddSourceCardResult =
    AddSourceCardResult(
        responseCode = responseCode,
        responseMessage = responseMessage,
        status = status,
        success = success,
        token = token,
    )

fun DestinationCardsNetworkResult.mapToDestinationCardsResultDomain() =
    DestinationCardsResult(
        destinationCards = destinationCards.map {
            it.mapToDestinationCardDomain()
        }
    )

fun DestinationCardNetwork.mapToDestinationCardDomain() =
    DestinationCard(
        id = id,
        pan = pan,
        title = title,
        isDeleted = isDeleted
    )

fun AddDestinationCardParam.mapToAddDestinationCardParamData() =
    AddDestinationCardNetworkParam(
        pan = pan,
        title = title,
        isDeleted = isDeleted
    )

fun EditDestinationCardParam.mapToEditDestinationCardParamData() =
    EditDestinationCardNetworkParam(
        pan = pan,
        title = title,
        isDeleted = isDeleted
    )