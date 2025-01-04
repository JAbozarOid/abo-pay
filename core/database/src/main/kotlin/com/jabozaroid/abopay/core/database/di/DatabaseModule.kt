package com.jabozaroid.abopay.core.database.di

/**
 * Created on 31,July,2024
 */

import android.content.Context
import androidx.room.Room
import com.jabozaroid.abopay.core.database.AboPayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): AboPayDatabase = Room.databaseBuilder(
        context,
        AboPayDatabase::class.java,
        "abo-pay-database",
    ).build()
}
