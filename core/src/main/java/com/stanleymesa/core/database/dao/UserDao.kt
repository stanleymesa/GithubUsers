package com.stanleymesa.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.stanleymesa.core.database.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity WHERE login = :username")
    suspend fun getUser(username: String): UserEntity?

    @Upsert
    suspend fun upsert(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

}