package com.stanleymesa.detail_domain.repository

import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.detail_domain.model.UserRepos
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getUser(username: String): Flow<Resource<User>>
    suspend fun getUserRepos(username: String): Flow<Resource<List<UserRepos>>>
}