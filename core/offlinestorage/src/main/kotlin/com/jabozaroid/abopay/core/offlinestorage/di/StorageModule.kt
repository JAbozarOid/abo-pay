package com.jabozaroid.abopay.core.offlinestorage.di

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.OfflineStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SecureStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.offlinestorage.storage.datastorestorage.DataStoreStorage
import com.jabozaroid.abopay.core.offlinestorage.storage.securestorage.KeyStoreSecureStorage
import com.jabozaroid.abopay.core.offlinestorage.storage.sharedpreferences.SharedPreferencesStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun bindSharedPreferencesStorage(sharedPreferencesStorage: SharedPreferencesStorage): SharedPrefStorage

    @Binds
    abstract fun bindDataStoreStorage(dataStoreStorage: DataStoreStorage): OfflineStorage

    @Binds
    abstract fun bindSecurityStorageStorage(keyStoreSecureStorage: KeyStoreSecureStorage): SecureStorage

}