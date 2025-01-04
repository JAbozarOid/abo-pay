package com.jabozaroid.abopay.feature.cardtocard.c2c.preview

import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.common.R

/**
 * Created on 20,November,2024
 */

val cardList = mutableListOf(
    Card(
        ownerName = "محمد حسینی",
        number = "6037997175607630",
        token = "1",
        cvv2 = CVV2(number = "456"),
        month = Month(number = "09"),
        year = Year("1409"),
        bankName = R.string.melli_bank,
        colorUp = R.color.melli_up,
        colorDown = R.color.melli_Down,
        icon = R.drawable.card_icon_color_melli,
        isDefault = true
    ),
    Card(
        ownerName = "ابوذر رقیب دوست",
        number = "5892101502849876",
        token = "2",
        cvv2 = CVV2(number = "14587"),
        month = Month(number = "02"),
        year = Year("1406"),
        bankName = R.string.sepah_bank,
        colorUp = R.color.sepah_up,
        colorDown = R.color.sepah_dwon,
        icon = R.drawable.card_icon_color_sepah

    ),
    Card(
        ownerName = "عطیه فریدونی",
        number = "6104338921525206",
        token = "3",
        cvv2 = CVV2(number = "963"),
        month = Month(number = "10"),
        year = Year("1403"),
        bankName = R.string.mellat_bank,
        colorUp = R.color.melat_up,
        colorDown = R.color.melat_down,
        icon = R.drawable.card_icon_color_mellat

    ),
    Card(
        ownerName = "ساناز رمضان",
        number = "6037998284328142",
        token = "4",
        cvv2 = CVV2(number = "6598"),
        month = Month(number = "11"),
        year = Year("1490"),
        bankName = R.string.melli_bank,
        colorUp = R.color.melli_up,
        colorDown = R.color.melli_Down,
        icon = R.drawable.card_icon_color_melli

    )
)