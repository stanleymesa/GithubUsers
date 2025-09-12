package com.stanleymesa.detail_data.model

import com.squareup.moshi.Json

data class UserReposDTO(

    @Json(name = "fork")
    val fork: Boolean? = null,

    @Json(name = "full_name")
    val fullName: String? = null,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    @Json(name = "stargazers_count")
    val stargazersCount: Int? = null,

    @Json(name = "pushed_at")
    val pushedAt: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "language")
    val language: String? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "watchers_count")
    val watchersCount: Int? = null,

    @Json(name = "forks_count")
    val forksCount: Int? = null,

    @Json(name = "owner")
    val owner: Owner? = null
) {
    data class Owner(
        @Json(name = "login")
        val login: String? = null,

        @Json(name = "id")
        val id: Int? = null,
    )
}
