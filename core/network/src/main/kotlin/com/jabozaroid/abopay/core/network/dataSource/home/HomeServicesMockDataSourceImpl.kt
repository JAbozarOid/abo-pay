package com.jabozaroid.abopay.core.network.dataSource.home

import com.jabozaroid.abopay.core.data.dataSource.home.HomeServicesRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.home.Category
import com.jabozaroid.abopay.core.domain.model.home.CategoryItem
import com.jabozaroid.abopay.core.domain.model.home.HomeService
import javax.inject.Inject

/**
 * Created on 31,August,2024
 */
class HomeServicesMockDataSourceImpl @Inject constructor() : HomeServicesRemoteDataSource {

    override suspend fun getHomeService(): AboPayResult<HomeService?> =
        AboPayResult.AboPaySuccess(
            HomeService(
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
                                id = 5,
                                title = "اینترنت",
                                iconName = "http://172.24.34.69/Contents/images/bank/icons/toll.png",
                                isActive = true,
                                appType = 0,
                                url = ""
                            ),
                            CategoryItem(
                                categoryId = 3,
                                id = 5,
                                title = "موجودی",
                                iconName = "http://172.24.34.69/Contents/images/bank/icons/toll.png",
                                isActive = true,
                                appType = 0,
                                url = ""
                            )
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
                                title = "مدیریت کارت",
                                iconName = "http://172.24.34.69/Contents/images/bank/icons/cardmanagement.png",
                                isActive = true,
                                appType = 0,
                                url = ""
                            ),
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
        )


//    override suspend fun getHomeService(): ResultApp<HomeService?> = ResultApp.Error(Throwable("This is a test Error"))
}