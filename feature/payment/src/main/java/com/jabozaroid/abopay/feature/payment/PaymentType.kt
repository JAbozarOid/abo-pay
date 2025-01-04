package com.jabozaroid.abopay.feature.payment

/**
 *  Created on 11/16/2024
 **/

enum class PaymentType(var id: Int, var title: String) {
    CHARGE(1, "خرید رمز شارژ"),
    BILL(3, "پرداخت قبض"),
    BUY(4, "خرید"),
    TOPUP(2, "خرید شارژ مستقیم"),
    TRAVEL_TOLL(15, " پرداخت عوارض خروج "),
    BALANCE(6, "دریافت موجودی"),
    CARD_TO_CARD(22, "کارت به کارت"),
    CREDIT_WALLET(21, "شارژ کیف پول"),
    INTERNET(2, "خرید بسته اینترنت"),
    LINK_PAYMENT(18, "پرداخت با لینک"),
}