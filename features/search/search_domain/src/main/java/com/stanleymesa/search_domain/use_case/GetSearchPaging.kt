package com.stanleymesa.search_domain.use_case

import androidx.paging.PagingData
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.search_domain.model.UserPayload
import com.stanleymesa.search_domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchPaging(private val repository: SearchRepository) {
    suspend operator fun invoke(payload: UserPayload): Flow<PagingData<User>> =
        repository.getUserSearchPaging(payload)
}