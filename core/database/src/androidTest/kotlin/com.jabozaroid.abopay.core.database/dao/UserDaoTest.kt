package com.jabozaroid.abopay.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jabozaroid.abopay.core.database.AboPayDatabase
import com.jabozaroid.abopay.core.database.model.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class UserDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var db: AboPayDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AboPayDatabase::class.java,
        ).build()
        userDao = db.userDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun userDao_adds_user() = runTest {
        val userEntity =
            UserEntity(
                id = "1",
                name = "Atieh",
                lastName = "Fereydooni"
            )
        val insertedUser = userDao.insertUser(
            userEntity
        )

        assertEquals(
            1,
            insertedUser
        )
    }

    @Test
    fun userDao_fetches_user_by_id() = runTest {
        val userEntity =
            UserEntity(
                id = "1",
                name = "Atieh",
                lastName = "Fereydooni"
            )
        userDao.insertUser(
            userEntity
        )

        val savedUserEntity = userDao.getUser(userEntity.id)

        assertEquals(
            userEntity.name,
            savedUserEntity.first().name
        )
    }

    @Test
    fun userDao_fetches_users() = runTest {
        val userEntities = listOf(
            UserEntity(
                id = "1",
                name = "Atieh",
                lastName = "Fereydooni"
            ),
            UserEntity(
                id = "2",
                name = "Sanaz",
                lastName = "RamezanPour"
            )
        )
        userDao.insertUsers(
            userEntities
        )

        val savedUserEntities = userDao.getUsers().first()

        assertEquals(
            listOf("1", "2"),
            savedUserEntities.map {
                it.id
            }
        )
    }

    @Test
    fun userDao_deletes_user() = runTest {
        val userEntities = listOf(
            UserEntity(
                id = "1",
                name = "Atieh",
                lastName = "Fereydooni"
            ),
            UserEntity(
                id = "2",
                name = "Sanaz",
                lastName = "RamezanPour"
            )
        )
        userDao.insertUsers(
            userEntities
        )

        userDao.deleteUser(userEntities[0])

        val savedUserEntities = userDao.getUsers().first()

        assertEquals(
            1,
            savedUserEntities.size
        )
        assertNotEquals(
            userEntities[0].id,
            savedUserEntities[0].id
        )
    }

    @Test
    fun userDao_update_user() = runTest {
        val userEntities = listOf(
            UserEntity(
                id = "1",
                name = "Atieh",
                lastName = "Fereydooni"
            ),
            UserEntity(
                id = "2",
                name = "Sanaz",
                lastName = "RamezanPour"
            )
        )
        userDao.insertUsers(
            userEntities
        )

        userDao.updateUser(
            UserEntity(
            id = "1",
            name = "Atieh1",
            lastName = "Fereydooni1"
        )
        )

        val updatedUserEntities = userDao.getUsers().first()

        assertNotEquals(
            userEntities[0].name,
            updatedUserEntities[0].name
        )
    }

    @Test
    fun userDao_update_user_by_name_lastName() = runTest {
        val userEntities = listOf(
            UserEntity(
                id = "1",
                name = "Atieh",
                lastName = "Fereydooni"
            ),
            UserEntity(
                id = "2",
                name = "Sanaz",
                lastName = "RamezanPour"
            )
        )
        userDao.insertUsers(
            userEntities
        )

        userDao.updateUserByName(
            id = "1",
            name = "Atieh1",
            lastName = "Fereydooni1"
        )

        val updatedUserEntities = userDao.getUsers().first()

        assertNotEquals(
            userEntities[0].name,
            updatedUserEntities[0].name
        )
    }

}