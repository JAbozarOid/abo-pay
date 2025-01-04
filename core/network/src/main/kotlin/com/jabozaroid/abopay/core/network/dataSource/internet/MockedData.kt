package com.jabozaroid.abopay.core.network.dataSource.internet

import com.jabozaroid.abopay.core.domain.model.internet.CategoryItem
import com.jabozaroid.abopay.core.domain.model.internet.OperatorItem
import com.jabozaroid.abopay.core.domain.model.internet.ServiceItem
import com.jabozaroid.abopay.core.domain.model.internet.TopUpInternetResult

/**
 * Created on 16,October,2024
 */

val mockedInternetTopUpCatalogs = TopUpInternetResult(
    isActiveTransportation = true,
    operators = listOf(
        OperatorItem(
            agreement = " کاربر گرامی ؛ چنانچه بسته اینترنت سیم کارت شما فعال و یک بسته اینترنت رزرو شده دارید، امکان خرید بسته جدید وجود ندارد و در صورت کسر وجه در کوتاه ترین زمان عودت داده خواهد شد .",
            categories =
            listOf(
                CategoryItem(
                    code = 2,
                    name = "Internet",
                    services = listOf(
                        ServiceItem(
                            amount = 58900.0,
                            code = 1,
                            durationCode = "07",
                            durationId = 14,
                            durationTitle = "هفتگی",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت آلفا + 7 روزه200مگابايت -اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 73700.0,
                            code = 2,
                            durationCode = "07",
                            durationId = 14,
                            durationTitle = "هفتگی",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت آلفا + 7 روزه 300 مگابايت -اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 111200.0,
                            code = 13,
                            durationCode = "07",
                            durationId = 14,
                            durationTitle = "هفتگی",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت آلفا + 7 روزه 0.75 مگابايت -اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 261300.0,
                            code = 20,
                            durationCode = "07",
                            durationId = 14,
                            durationTitle = "هفتگی",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت 7 روزه 6 گيگابايت اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 134000.0,
                            code = 21,
                            durationCode = "30",
                            durationId = 16,
                            durationTitle = "یک ماهه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت آلفا + يک ماهه12گيگ6تا12ظهر -اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 85700.0,
                            code = 28,
                            durationCode = "01",
                            durationId = 12,
                            durationTitle = "روزانه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت 1 روزه 1 گيگابايت اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 21400.0,
                            code = 34,
                            durationCode = "01",
                            durationId = 12,
                            durationTitle = "روزانه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = " بسته اينترنت 1 روزه 60 مگابايت اعتباری",
                            vat = 10.0
                        ), ServiceItem(
                            amount = 132600.0,
                            code = 37,
                            durationCode = "01",
                            durationId = 12,
                            durationTitle = "روزانه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = "بسته اينترنت 1 روزه 3 گيگابايت -اعتباری",
                            vat = 10.0
                        )
                    )
                )
            ), categoryCodesLst = listOf(
                "0910",
                "0911",
                "0912",
                "0913",
                "0914",
                "0915",
                "0916",
                "0917",
                "0918",
                "0919",
                "0990",
                "0991",
                "0992",
                "0993",
                "09967",
                "09968",
                "09969"
            ),
            code = 919,
            logoUrl = "https://cdn2.ivaapp.com/Contents/images/0919.png",
            mobileOperatorNameFa = "همراه اول",
            name = "MCI"
        ),
        OperatorItem(
            agreement = "null",
            categories = listOf(
                CategoryItem(
                    code = 2, name = "Internet",
                    services = listOf(
                        ServiceItem(
                            amount = 285420.0,
                            code = 57,
                            durationCode = "30",
                            durationId = 6,
                            durationTitle = "یک ماهه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = "بسته یکماهه 4 گیگابایت-اعتباری",
                            vat = 10.0
                        ),
                        ServiceItem(
                            amount = 316240.0,
                            code = 58,
                            durationCode = "30",
                            durationId = 6,
                            durationTitle = "یک ماهه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = "بسته یکماهه 5 گیگابایت-اعتباری",
                            vat = 10.0
                        ),
                        ServiceItem(
                            amount = 380000.0,
                            code = 150,
                            durationCode = "30",
                            durationId = 6,
                            durationTitle = "یک ماهه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = "بسته یکماهه 7 گیگابایت-اعتباری",
                            vat = 10.0
                        ),
                        ServiceItem(
                            amount = 482400.0,
                            code = 171,
                            durationCode = "60",
                            durationId = 7,
                            durationTitle = "دو ماهه",
                            enabled = true,
                            isFixedAmount = true,
                            isWonderful = false,
                            maxAmount = 0.0,
                            minAmount = 0.0,
                            name = "بسته 60 روزه 10 گیگابایت-اعتباری",
                            vat = 10.0
                        )
                    )
                )
            ),
            categoryCodesLst = listOf(
                "0930",
                "0933",
                "0935",
                "0933",
                "0930",
                "0936",
                "0937",
                "0939",
                "0938",
                "0901",
                "0902",
                "0903",
                "0904",
                "0905",
                "0941"
            ),
            code = 935,
            logoUrl = "https://cdn2.ivaapp.com/Contents/images/0935.png",
            mobileOperatorNameFa = "ایرانسل",
            name = "MtnIrancell"
        )
    )
)