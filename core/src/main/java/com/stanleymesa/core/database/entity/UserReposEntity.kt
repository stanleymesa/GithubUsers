package com.stanleymesa.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserReposEntity(
    @PrimaryKey
    val id: Int,
    val fork: Boolean,
    val fullName: String,
    val updatedAt: String,
    val stargazersCount: Int,
    val pushedAt: String,
    val name: String,
    val description: String,
    val createdAt: String,
    val language: String,
    val watchersCount: Int,
    val forksCount: Int,
    @Embedded(prefix = "owner_")
    val owner: Owner
) {
    data class Owner(
        val login: String,
        val id: Int,
    ) {
        companion object Companion {
            fun empty() = Owner(login = "", id = 0)
        }
    }
}
