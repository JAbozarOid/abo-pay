package com.jabozaroid.abopay.feature.payment.paymentConfirmation.preview

import com.jabozaroid.abopay.core.common.model.CommonItems
import com.jabozaroid.abopay.core.common.model.MetaDataModel
import com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel
import com.jabozaroid.abopay.feature.payment.R

/**
 * Created on 20,November,2024
 */

val paymentConfirmationModel = com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
    commonItems = com.jabozaroid.abopay.core.common.model.CommonItems(
        iconTitle = "قبض مخابرات",
        amount = "250000",
        iconUrl = "",
        icon = R.drawable.ic_mokhaberat,
        toolbarTitle = "پرداخت قبض",
        vat = "15"
    ),
    paymentItems = mutableListOf(),
    visualItems = listOf(
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شَماره تلفن",
            "021454648"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شَماره قبض",
            "0214545656565648"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شناسه قبض ",
            "465656554648"
        ),
        com.jabozaroid.abopay.core.common.model.MetaDataModel(
            "شناسه پرداخت",
            "000986343565"
        ), com.jabozaroid.abopay.core.common.model.MetaDataModel(
            " کارمزد (ریال)",
            "رایگان"
        )
    )
)