package com.jabozaroid.abopay.core.common.infotype.di

import com.jabozaroid.abopay.core.common.BuildConfig
import com.jabozaroid.abopay.core.common.infotype.enums.InfoType.VERSION_CODE
import com.jabozaroid.abopay.core.common.infotype.enums.InfoType.VERSION_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * Created on 14,August,2024
 */

@Module
@InstallIn(SingletonComponent::class)
class InfoModule {

    @IntoMap
    @com.jabozaroid.abopay.core.common.infotype.di.InfoKey(VERSION_NAME)
    @Provides
    fun provideVersionName(): String = BuildConfig.VERSION_NAME

    @IntoMap
    @com.jabozaroid.abopay.core.common.infotype.di.InfoKey(VERSION_CODE)
    @Provides
    fun provideVersionCode(): String = BuildConfig.VERSION_CODE.toString()

}
