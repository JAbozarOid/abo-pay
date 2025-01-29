package com.jabozaroid.abopay.core.ui.navigation

object ApplicationRoutes {


    //region Graphs
    const val introGraphRoute = "intro_graph_route"
    const val homeGraphRoute = "home_graph_route"
    const val webViewGraphRoute = "web_view_graph_route"
    const val loginGraphRoute = "login_graph_route"
    const val verifyOtpGraphRoute = "verify_otp_graph_route"
    const val chargeGraphRoute = "charge_graph_route"
    const val internetGraphRoute = "internet_graph_route"
    const val billGraphRoute = "bill_graph_route"
    const val paymentConfirmationGraphRoute = "payment_confirmation_graph_route"
    const val paymentGraphRoute = "payment_graph_route"
    const val paymentReceiptGraphRoute = "receipt_graph_route"
    const val cardToCardGraphRoute = "card_to_card_graph_route"
    const val balanceGraphRoute = "balance_graph_route"
    const val c2cRecieptGraphRoute = "c2c_receipt_graph_route"
    const val c2cConfirmationGraphRoute = "c2c_confirmation_graph_route"
    const val cardManagementGraphRoute = "card_management_graph_route"
    const val kahrobaGraphRoute = "kahroba_graph_route"
    const val messengerGraphRoute = "messenger_graph_route"
    //endregion


    //region Screens
    const val introScreenRoute = "intro_screen_route"
    const val homeScreenRoute = "home_screen_route"
    const val secondHomeScreenRoute = "second_home_screen_route"
    const val webViewScreenRoute = "web_view_screen_route"
    const val loginScreenRoute = "login_screen_route"
    const val verifyOtpScreenRoute = "verify_otp_screen_route"
    const val chargeScreenRoute = "charge_screen_route"
    const val internetScreenRoute = "internet_screen_route"
    const val InternetSelectionListRoute = "internet_selection_list_route"
    const val billScreenRoute = "bill_screen_route"
    const val paymentConfirmationScreenRoute = "payment_confirmation_screen_route"
    const val paymentScreenRoute = "payment_screen_route"
    const val paymentReceiptScreenRoute = "payment_receipt_screen_route"
    const val cardToCardScreenRoute = "card_to_card_screen_route"
    const val balanceScreenRoute = "balance_screen_route"
    const val c2cReceiptScreenRoute = "c2c_receipt_screen_route"
    const val c2cConfrimationScreenRoute = "c2c_confirmation_screen_route"
    const val cardManagementScreenRoute = "card_management_screen_route"
    const val kahrobaScreenRoute = "kahroba_screen_route"
    const val nfcScreenRoute = "nfc_screen_route"
    const val kahrobaAuthScreenRoute = "kahroba_auth_screen_route"
    const val messengerScreenRoute = "messenger_screen_route"
    //endregion

    //region Params
    val introToHomeParams = "/{${NavigationParam.PHONE_NUMBER}}"
    val webViewParam = "/{${NavigationParam.WEB_VIEW_ITEM}}"
    val loginParam = "/{${NavigationParam.MOBILE}}"
    val internetParam =
        "/{${NavigationParam.SELECTED_OPERATOR}}/{${NavigationParam.SIM_TYPE}}/{${NavigationParam.OPERATOR_LOGO}}"
    val paymentConfirmationParam = "/{${NavigationParam.PAYMENT_CONFIRMATION_MODEL}}"
    val receiptModelParam = "/{${NavigationParam.RECEIPT_MODEL}}"
    val paymentModelParam = "/{${NavigationParam.PAYMENT_MODEL}}"
    //endregion

}