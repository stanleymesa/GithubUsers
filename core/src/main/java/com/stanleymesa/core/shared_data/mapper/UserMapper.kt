package com.stanleymesa.core.shared_data.mapper

import com.stanleymesa.core.database.entity.UserEntity
import com.stanleymesa.core.shared_data.dto.UserDTO
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.util.extentions.orZero

object UserMapper {

    fun UserDTO?.toEntity() = UserEntity(
        id = this?.id.orZero(),
        name = this?.name.orEmpty(),
        email = this?.email.orEmpty(),
        twitterUsername = this?.twitterUsername.orEmpty(),
        bio = this?.bio.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        login = this?.login.orEmpty(),
        blog = this?.blog.orEmpty(),
        publicGists = this?.publicGists.orZero(),
        privateGists = this?.privateGists.orZero(),
        totalPrivateRepos = this?.totalPrivateRepos.orZero(),
        followers = this?.followers.orZero(),
        following = this?.following.orZero(),
        publicRepos = this?.publicRepos.orZero(),
        collaborators = this?.collaborators.orZero(),
        ownedPrivateRepos = this?.ownedPrivateRepos.orZero(),
        avatarUrl = this?.avatarUrl.orEmpty(),
        htmlUrl = this?.htmlUrl.orEmpty(),
        company = this?.company.orEmpty(),
        location = this?.location.orEmpty(),
    )

    fun UserDTO?.toDomainModel() = User(
        id = this?.id.orZero(),
        name = this?.name.orEmpty(),
        email = this?.email.orEmpty(),
        twitterUsername = this?.twitterUsername.orEmpty(),
        bio = this?.bio.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        login = this?.login.orEmpty(),
        blog = this?.blog.orEmpty(),
        publicGists = this?.publicGists.orZero(),
        privateGists = this?.privateGists.orZero(),
        totalPrivateRepos = this?.totalPrivateRepos.orZero(),
        followers = this?.followers.orZero(),
        following = this?.following.orZero(),
        publicRepos = this?.publicRepos.orZero(),
        collaborators = this?.collaborators.orZero(),
        ownedPrivateRepos = this?.ownedPrivateRepos.orZero(),
        avatarUrl = this?.avatarUrl.orEmpty(),
        htmlUrl = this?.htmlUrl.orEmpty(),
        company = this?.company.orEmpty(),
        location = this?.location.orEmpty(),
    )

    fun UserEntity.toDomainModel() = User(
        id = this.id,
        name = this.name,
        email = this.email,
        twitterUsername = this.twitterUsername,
        bio = this.bio,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        login = this.login,
        blog = this.blog,
        publicGists = this.publicGists,
        privateGists = this.privateGists,
        totalPrivateRepos = this.totalPrivateRepos,
        followers = this.followers,
        following = this.following,
        publicRepos = this.publicRepos,
        collaborators = this.collaborators,
        ownedPrivateRepos = this.ownedPrivateRepos,
        avatarUrl = this.avatarUrl,
        htmlUrl = this.htmlUrl,
        company = this.company,
        location = this.location,
    )

}