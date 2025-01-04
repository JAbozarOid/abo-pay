package com.jabozaroid.abopay.core.database.dao

/**
 * Created on 30,July,2024
 */

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jabozaroid.abopay.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query(value = "SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>

    @Query(value = "SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET name = :name, lastName= :lastName WHERE id=:id")
    suspend fun updateUserByName(id: String, name: String, lastName: String)

}