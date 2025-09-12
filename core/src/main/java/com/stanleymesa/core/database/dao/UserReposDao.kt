package com.stanleymesa.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.stanleymesa.core.database.entity.UserReposEntity

@Dao
interface UserReposDao {

    @Query("SELECT * FROM userreposentity WHERE owner_login = :username")
    suspend fun getUserRepos(username: String): List<UserReposEntity>

    @Upsert
    suspend fun upsertAll(userReposEntities: List<UserReposEntity>)

    @Query("DELETE FROM userreposentity")
    suspend fun deleteAll()

}