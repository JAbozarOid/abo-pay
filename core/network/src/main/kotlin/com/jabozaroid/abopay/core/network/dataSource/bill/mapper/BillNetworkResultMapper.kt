package com.jabozaroid.abopay.core.network.dataSource.bill.mapper

import com.jabozaroid.abopay.core.domain.model.bill.AvailablePaymentMethodResult
import com.jabozaroid.abopay.core.domain.model.bill.BillTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.SpecResult
import com.jabozaroid.abopay.core.network.model.bill.AvailablePaymentMethodNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillTypeNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillsTypeNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.SpecNetworkResult

fun BillsTypeNetworkResult.mapBillsTypeToDomainModel() =
    BillsTypeResult(billTypes = billTypes.map {
        it.mapToDomainModel()
    })


fun BillTypeNetworkResult.mapToDomainModel() =
    BillTypeResult(
        id = id,
        billTypeName = billTypeName,
        billTypeDesc = billTypeDesc,
        parameterName = parameterName,
        logo = logo,
        darkLogo = darkLogo,
        lightLogo = lightLogo,
        addToBillProfile = addToBillProfile,
        specResult = specNetworkResult?.maoToDomainModel(),
        canInquiry = canInquiry,
        showNotification = showNotification,
        showBarcode = showBarcode,
        parameterMaxLength = parameterMaxLength,
        addToRepeatTransaction = addToRepeatTransaction,
        wage = wage,
        availablePaymentMethodResults = availablePaymentMethodNetworkResults.map {
            it.mapToDomainModel()
        }
    )

fun SpecNetworkResult.maoToDomainModel() =
    SpecResult(
        type = type,
        standardType = standardType,
        validationType = validationType,
        input = input
    )

fun AvailablePaymentMethodNetworkResult.mapToDomainModel() =
    AvailablePaymentMethodResult(
        title = title,
        id = id,
        titleFa = titleFa
    )