package com.stanleymesa.search_domain.repository

import androidx.paging.PagingData
import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.search_domain.model.UserPayload
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getUserSearch(
        page: Int,
        pageSize: Int,
        payload: UserPayload,
    ): Flow<Resource<List<User>>>

    suspend fun getUserSearchPaging(payload: UserPayload): Flow<PagingData<User>>

}