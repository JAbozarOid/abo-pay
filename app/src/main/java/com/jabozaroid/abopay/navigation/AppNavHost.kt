package com.jabozaroid.abopay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.jabozaroid.abopay.application.AppState
import com.jabozaroid.abopay.feature.balance.balanceGraph
import com.jabozaroid.abopay.feature.bill.billGraph
import com.jabozaroid.abopay.feature.cardmanagement.cardManagementGraph
import com.jabozaroid.abopay.feature.cardtocard.cardToCardGraph
import com.jabozaroid.abopay.feature.charge.chargeGraph
import com.jabozaroid.abopay.feature.home.homeGraph
import com.jabozaroid.abopay.feature.internet.internetGraph
import com.jabozaroid.abopay.feature.intro.introGraph
import com.jabozaroid.abopay.feature.kahroba.kahrobaGraph
import com.jabozaroid.abopay.feature.login.loginGraph
import com.jabozaroid.abopay.feature.messenger.messengerGraph
import com.jabozaroid.abopay.feature.payment.paymentGraph
import com.jabozaroid.abopay.feature.webview.webViewGraph

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String
) {
        NavHost(
            navController = appState.navController,
            startDestination = startDestination,
            modifier = modifier,
        ) {
            introGraph()
            homeGraph()
            webViewGraph()
            loginGraph()
            billGraph()
            chargeGraph()
            internetGraph()
            paymentGraph()
            cardToCardGraph()
            balanceGraph()
            cardManagementGraph()
            kahrobaGraph()
            messengerGraph()
        }
}