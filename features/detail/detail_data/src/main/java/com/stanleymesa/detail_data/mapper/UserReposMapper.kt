package com.stanleymesa.detail_data.mapper

import com.stanleymesa.core.database.entity.UserReposEntity
import com.stanleymesa.core.util.extentions.orFalse
import com.stanleymesa.core.util.extentions.orZero
import com.stanleymesa.detail_data.model.UserReposDTO
import com.stanleymesa.detail_domain.model.UserRepos

object UserReposMapper {

    fun UserReposDTO?.toEntity() = UserReposEntity(
        id = this?.id.orZero(),
        fork = this?.fork.orFalse(),
        fullName = this?.fullName.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        pushedAt = this?.pushedAt.orEmpty(),
        name = this?.name.orEmpty(),
        description = this?.description.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        language = this?.language.orEmpty(),
        stargazersCount = this?.stargazersCount.orZero(),
        watchersCount = this?.watchersCount.orZero(),
        forksCount = this?.forksCount.orZero(),
        owner = UserReposEntity.Owner(
            id = this?.owner?.id.orZero(),
            login = this?.owner?.login.orEmpty(),
        )
    )

    fun UserReposDTO?.toDomainModel() = UserRepos(
        id = this?.id.orZero(),
        fork = this?.fork.orFalse(),
        fullName = this?.fullName.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        pushedAt = this?.pushedAt.orEmpty(),
        name = this?.name.orEmpty(),
        description = this?.description.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        language = this?.language.orEmpty(),
        stargazersCount = this?.stargazersCount.orZero(),
        watchersCount = this?.watchersCount.orZero(),
        forksCount = this?.forksCount.orZero(),
        owner = UserRepos.Owner(
            id = this?.owner?.id.orZero(),
            login = this?.owner?.login.orEmpty(),
        )
    )

    fun UserReposEntity.toDomainModel() = UserRepos(
        id = this.id,
        fork = this.fork,
        fullName = this.fullName,
        updatedAt = this.updatedAt,
        pushedAt = this.pushedAt,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
        language = this.language,
        stargazersCount = this.stargazersCount,
        watchersCount = this.watchersCount,
        forksCount = this.forksCount,
        owner = UserRepos.Owner(
            id = this.owner.id,
            login = this.owner.login,
        )
    )
}