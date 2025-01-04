package com.jabozaroid.abopay.core.network.di.module

import android.os.Build
import com.jabozaroid.abopay.core.network.di.annotation.InfoKey
import com.jabozaroid.abopay.core.network.helper.InfoType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
class InfoModule {

    @IntoMap
    @InfoKey(InfoType.ANDROID_VERSION)
    @Provides
    fun provideVersionCode() =
        Build.VERSION.RELEASE

    @IntoMap
    @InfoKey(InfoType.DEVICE_NAME)
    @Provides
    fun provideVersionName() =
        Build.DEVICE

    @IntoMap
    @InfoKey(InfoType.DEVICE_BRAND)
    @Provides
    fun provideMarket() = Build.MANUFACTURER

    @IntoMap
    @InfoKey(InfoType.DEVICE_MODEL)
    @Provides
    fun provideDeviceModel() = Build.MODEL
}