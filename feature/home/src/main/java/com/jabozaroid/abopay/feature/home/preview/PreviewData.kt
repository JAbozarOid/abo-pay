package com.jabozaroid.abopay.feature.home.preview

import com.jabozaroid.abopay.core.domain.model.home.Category
import com.jabozaroid.abopay.core.domain.model.home.CategoryItem
import com.jabozaroid.abopay.core.domain.model.home.HomeService

/**
 * Created on 20,November,2024
 */

val previewHomeService = HomeService(
    listOf(
        Category(
            id = 3,
            title = "خدمات پرکاربرد",
            isActive = true,
            description = "",
            backgroundColor = "#EDEDED",
            categoryItems = listOf(
                CategoryItem(
                    categoryId = 3,
                    id = 1,
                    title = "کارت به کارت",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/c2c.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 3,
                    id = 2,
                    title = "شارژ",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/charge.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 3,
                    id = 3,
                    title = "قبض",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/bill.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 3,
                    id = 4,
                    title = "عوارض خروج",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/toll.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 3,
                    id = 4,
                    title = "عوارض خروج",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/toll.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
            )
        ),
        Category(
            id = 1,
            title = "خدمات ویژه مالی",
            isActive = true,
            description = "نیاز به ارتقا سطح احراز هویت",
            backgroundColor = "#E5ECFF",
            categoryItems = listOf(
                CategoryItem(
                    categoryId = 2,
                    id = 1,
                    title = "افتتاح حساب",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/cardmanagement.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 2,
                    id = 2,
                    title = "انتقال وجه",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/moneytransfer.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 2,
                    id = 3,
                    title = "صورتحساب",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/cheque.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
                CategoryItem(
                    categoryId = 2,
                    id = 4,
                    title = "تمکن مالی",
                    iconName = "http://172.24.34.69/Contents/images/bank/icons/charity.png",
                    isActive = true,
                    appType = 0,
                    url = ""
                ),
            )
        )
    )
)

val previewHomeCategoryItem = CategoryItem(
    appType = 0,
    categoryId = 1,
    iconName = "account",
    id = 1,
    isActive = true,
    title = "افتتاح حساب",
    url = "https://webmail.sadadpsp.ir/"
)