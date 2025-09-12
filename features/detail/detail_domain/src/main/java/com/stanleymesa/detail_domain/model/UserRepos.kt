package com.stanleymesa.detail_domain.model

data class UserRepos(
    val fork: Boolean,
    val fullName: String,
    val updatedAt: String,
    val stargazersCount: Int,
    val pushedAt: String,
    val name: String,
    val description: String,
    val createdAt: String,
    val language: String,
    val id: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val owner: Owner,
) {
    data class Owner(
        val login: String,
        val id: Int,
    )
}
