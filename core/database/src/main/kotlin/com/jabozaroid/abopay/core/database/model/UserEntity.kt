package com.jabozaroid.abopay.core.database.model

/**
 * Created on 30,July,2024
 */

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "users",
)
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val lastName: String
)

