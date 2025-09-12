package com.stanleymesa.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stanleymesa.core.database.converter.Converters
import com.stanleymesa.core.database.dao.UserDao
import com.stanleymesa.core.database.dao.UserReposDao
import com.stanleymesa.core.database.entity.UserEntity
import com.stanleymesa.core.database.entity.UserReposEntity

@Database(
    entities = [UserEntity::class, UserReposEntity::class],
    version = 1,
    exportSchema = true,
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val userReposDao: UserReposDao
}