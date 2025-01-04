package com.jabozaroid.abopay.core.database

/**
 * Created on 31,July,2024
 */

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jabozaroid.abopay.core.database.dao.UserDao
import com.jabozaroid.abopay.core.database.model.UserEntity
import com.jabozaroid.abopay.core.database.util.UserTypeConverter


@Database(
    entities = [UserEntity::class],
    version = 1,
    //TODO: This part is used when we need some changes in database in a version change
    /**
    autoMigrations = [
    AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class)
    ],
     */
    exportSchema = true
)
@TypeConverters(
    UserTypeConverter::class
)
abstract class AboPayDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}