package com.stanleymesa.detail_domain.use_case

import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.detail_domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow

class GetUser(private val repository: DetailRepository) {
    suspend operator fun invoke(username: String): Flow<Resource<User>> =
        repository.getUser(username)
}