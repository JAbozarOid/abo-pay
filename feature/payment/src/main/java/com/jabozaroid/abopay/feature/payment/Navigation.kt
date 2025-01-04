package com.jabozaroid.abopay.feature.payment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.payment.pay.view.PaymentScreen
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.view.PaymentConfirmationScreen
import com.jabozaroid.abopay.feature.payment.reciept.view.ReceiptScreen


/**
 *  Created on 10/21/2024
 **/
val paymentConfirmationScreen  = PaymentConfirmationScreen()
val paymentScreen  = PaymentScreen()
val receiptScreen  = ReceiptScreen()

fun NavGraphBuilder.paymentGraph() {

    navigation(
        route = ApplicationRoutes.paymentGraphRoute,
        startDestination = ApplicationRoutes.paymentConfirmationScreenRoute
    ) {

        registerDestination(paymentScreen)
        registerDestination(paymentConfirmationScreen)
        registerDestination(receiptScreen)


    }

}