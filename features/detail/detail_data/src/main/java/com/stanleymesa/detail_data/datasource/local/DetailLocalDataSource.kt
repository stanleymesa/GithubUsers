package com.stanleymesa.detail_data.datasource.local

import androidx.room.withTransaction
import com.stanleymesa.core.database.AppDatabase
import com.stanleymesa.core.database.dao.UserDao
import com.stanleymesa.core.database.dao.UserReposDao
import com.stanleymesa.core.database.entity.UserEntity
import com.stanleymesa.core.database.entity.UserReposEntity
import javax.inject.Inject

class DetailLocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val userReposDao: UserReposDao,
    private val appDatabase: AppDatabase
) {
    /** User */
    suspend fun getUser(username: String): UserEntity? = userDao.getUser(username)
    suspend fun upsertUser(user: UserEntity) = userDao.upsert(user)
    suspend fun deleteUser(user: UserEntity) = userDao.delete(user)

    /** User Repository */
    suspend fun getUserRepos(username: String): List<UserReposEntity> =
        userReposDao.getUserRepos(username)

    suspend fun upsertAllUserRepos(userReposEntities: List<UserReposEntity>) =
        userReposDao.upsertAll(userReposEntities)

    suspend fun deleteAllUserRepos() = userReposDao.deleteAll()

    suspend fun <R> transaction(block: suspend () -> R): R = appDatabase.withTransaction {
        block.invoke()
    }
}