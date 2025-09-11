package com.stanleymesa.core.shared_data.dto

import com.squareup.moshi.Json

data class UserDTO(

	@Json(name="twitter_username")
	val twitterUsername: String? = null,

	@Json(name="bio")
	val bio: String? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="login")
	val login: String? = null,

	@Json(name="blog")
	val blog: String? = null,

	@Json(name="public_gists")
	val publicGists: Int? = null,

	@Json(name="private_gists")
	val privateGists: Int? = null,

	@Json(name="total_private_repos")
	val totalPrivateRepos: Int? = null,

	@Json(name="followers")
	val followers: Int? = null,

	@Json(name="avatar_url")
	val avatarUrl: String? = null,

	@Json(name="updated_at")
	val updatedAt: String? = null,

	@Json(name="html_url")
	val htmlUrl: String? = null,

	@Json(name="following")
	val following: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="collaborators")
	val collaborators: Int? = null,

	@Json(name="company")
	val company: String? = null,

	@Json(name="location")
	val location: String? = null,

	@Json(name="owned_private_repos")
	val ownedPrivateRepos: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="public_repos")
	val publicRepos: Int? = null,

	@Json(name="email")
	val email: String? = null
)
