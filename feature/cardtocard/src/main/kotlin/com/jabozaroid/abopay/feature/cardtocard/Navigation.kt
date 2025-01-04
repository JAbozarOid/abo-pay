package com.jabozaroid.abopay.feature.cardtocard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.cardtocard.c2c.view.CardToCardScreen
import com.jabozaroid.abopay.feature.cardtocard.confirmation.view.ConfirmationScreen
import com.jabozaroid.abopay.feature.cardtocard.receipt.view.ReceiptScreen

/**
 *  Created on 9/29/2024
 **/


val cardToCardScreen = CardToCardScreen()
val receiptScreen = ReceiptScreen()
val confirmationScreen = ConfirmationScreen()

fun NavGraphBuilder.cardToCardGraph() {

    navigation(
        route = ApplicationRoutes.cardToCardGraphRoute,
        startDestination = ApplicationRoutes.cardToCardScreenRoute
    ) {

        registerDestination(cardToCardScreen)
        registerDestination(receiptScreen)
        registerDestination(confirmationScreen)
//        registerDestination(receiptScreen ,
//            listOf(navArgument(NavigationParam.C2C_TRANSFER_RESULT.name){
//                type = NavType.StringType
//            })
//        )

    }
}