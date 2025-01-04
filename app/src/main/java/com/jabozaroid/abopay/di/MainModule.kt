package com.jabozaroid.abopay.di

import com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider
import com.jabozaroid.abopay.core.common.dispatcher.StandardDispatcherProvider
import com.jabozaroid.abopay.main.view.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

// todo (abozar) : impl main module

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainModule {

    @Binds
    abstract fun bindStandardDispatcherProvider(standardDispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.StandardDispatcherProvider): com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider

}

@Module
@InstallIn(SingletonComponent::class)
internal object ActivityModule {
    @Provides
    @Named("MainActivity")
    fun providesMainActivity(): Class<*> {
        return MainActivity::class.java
    }
}