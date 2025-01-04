package com.jabozaroid.abopay.core.database.util

/**
 * Created on 30,July,2024
 */

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jabozaroid.abopay.core.database.model.UserEntity
import com.jabozaroid.abopay.core.domain.model.user.User

internal class UserTypeConverter {

    @TypeConverter
    fun toUser(value: String?): UserEntity {
        return Gson().fromJson(value, UserEntity::class.java)
    }

    @TypeConverter
    fun fromUser(value: User?): String {
        return Gson().toJson(value)
    }

}
