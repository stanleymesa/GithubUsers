package com.stanleymesa.detail_domain.use_case

import com.stanleymesa.core.network.Resource
import com.stanleymesa.detail_domain.model.UserRepos
import com.stanleymesa.detail_domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow

class GetUserRepos(private val repository: DetailRepository) {
    suspend operator fun invoke(username: String): Flow<Resource<List<UserRepos>>> =
        repository.getUserRepos(username)
}