package com.jabozaroid.abopay.core.database.di

/**
 * Created on 30,July,2024
 */

import com.jabozaroid.abopay.core.database.AboPayDatabase
import com.jabozaroid.abopay.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesUsersDao(
        database: AboPayDatabase,
    ): UserDao = database.userDao()


}
