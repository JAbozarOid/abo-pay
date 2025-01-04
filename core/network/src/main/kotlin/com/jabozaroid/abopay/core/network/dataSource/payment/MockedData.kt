package com.jabozaroid.abopay.core.network.dataSource.payment

import com.jabozaroid.abopay.core.domain.model.payment.KeyValue
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult

/**
 *  Created on 12/22/2024
 **/



val mockedPaymentResult = PaymentResult(
    items = mutableListOf(
        KeyValue(key ="مبلغ(ریال)" , value = "1880000" ),
        KeyValue(key ="شماره پیگیری" , value = "142565776"),
        KeyValue(key ="شماره ارجاع" , value = "6578484093837" ),
        KeyValue(key ="شماره تلفن" , value = "021 88776655" ),
        KeyValue(key ="شناسه قبض" , value = "4146804930158" ),
        KeyValue(key ="شناسه پرداخت" , value = "000013889054" ),
        KeyValue(key ="کارمزد (ریال)" , value =  "رایگان" ),
        KeyValue(key ="تاریخ-ساعت" , value = "1402-05-01-08:31:00" ),
)
)
