package com.stanleymesa.core.shared_data.model

data class User(
    val twitterUsername: String,
    val bio: String,
    val createdAt: String,
    val login: String,
    val blog: String,
    val publicGists: Int,
    val privateGists: Int,
    val totalPrivateRepos: Int,
    val followers: Int,
    val avatarUrl: String,
    val updatedAt: String,
    val htmlUrl: String,
    val following: Int,
    val name: String,
    val collaborators: Int,
    val company: String,
    val location: String,
    val ownedPrivateRepos: Int,
    val id: Int,
    val publicRepos: Int,
    val email: String
)
