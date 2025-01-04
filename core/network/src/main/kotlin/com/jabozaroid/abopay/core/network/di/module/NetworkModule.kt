package com.jabozaroid.abopay.core.network.di.module

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.network.api.auth.AuthenticationApi
import com.jabozaroid.abopay.core.network.api.balance.BalanceApi
import com.jabozaroid.abopay.core.network.api.bill.BillApi
import com.jabozaroid.abopay.core.network.api.c2c.CardToCardApi
import com.jabozaroid.abopay.core.network.api.cardmanagement.CardManagementApi
import com.jabozaroid.abopay.core.network.api.charge.ChargeApi
import com.jabozaroid.abopay.core.network.api.home.HomeServicesApi
import com.jabozaroid.abopay.core.network.api.internet.InternetApi
import com.jabozaroid.abopay.core.network.api.payment.PaymentApi
import com.jabozaroid.abopay.core.network.di.annotation.Order
import com.jabozaroid.abopay.core.network.helper.InfoType
import com.jabozaroid.abopay.core.network.helper.RetrofitHelper
import com.jabozaroid.abopay.core.network.interceptor.AuthenticationInterceptor
import com.jabozaroid.abopay.core.network.interceptor.InformationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.Interceptor
import retrofit2.Retrofit

private const val BASE_URL = "http://172.24.34.95:1080/"
//private const val BASE_URL = "http://172.24.34.67:3009/"
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    fun provideDefaultRetrofit(
        infoMap: Map<InfoType, String>,
        interceptorsMap: Map<Int, @JvmSuppressWildcards Interceptor>,
    ): Retrofit {
        return RetrofitHelper.createRetrofit(
            BASE_URL,
            interceptorMap = interceptorsMap
        )
    }

    //region Interceptor
    @Order(1)
    @IntoMap
    @Provides
    fun provideAuthenticationInterceptor(
        sharedPreferences: SharedPrefStorage,
    ): Interceptor {
        return AuthenticationInterceptor(sharedPreferences)
    }

    @Provides
    @IntoMap
    @Order(2)
    fun provideInformationInterceptor(info: Map<InfoType, String>): Interceptor {
        return InformationInterceptor(info)
    }
    //endregion

    //region Api

    @Provides
    fun providerAuthenticationApi(
        retrofit: Retrofit,
    ): AuthenticationApi {
        return retrofit.create(AuthenticationApi::class.java)
    }

    @Provides
    fun provideChargeApi(retrofit: Retrofit) : ChargeApi {
        return retrofit.create(ChargeApi::class.java)
    }

    @Provides
    fun providerBillApi(
        retrofit: Retrofit,
    ): BillApi {
        return retrofit.create(BillApi::class.java)
    }


    @Provides
    fun providerHomeServicesApi(
        retrofit: Retrofit,
    ): HomeServicesApi {
        return retrofit.create(HomeServicesApi::class.java)
    }

    @Provides
    fun providerInternetServicesApi(
        retrofit: Retrofit,
    ): InternetApi {
        return retrofit.create(InternetApi::class.java)
    }

    @Provides
    fun providerBalanceApi(
        retrofit: Retrofit,
    ): BalanceApi {
        return retrofit.create(BalanceApi::class.java)
    }

    @Provides
    fun providerC2CServicesApi(
        retrofit: Retrofit,
    ): CardToCardApi {
        return retrofit.create(CardToCardApi::class.java)
    }

    @Provides
    fun providerCardManagementApi(
        retrofit: Retrofit,
    ): CardManagementApi {
        return retrofit.create(CardManagementApi::class.java)
    }

    @Provides
    fun providerPaymentServicesApi(
        retrofit: Retrofit,
    ): PaymentApi {
        return retrofit.create(PaymentApi::class.java)
    }
    //endregion
}