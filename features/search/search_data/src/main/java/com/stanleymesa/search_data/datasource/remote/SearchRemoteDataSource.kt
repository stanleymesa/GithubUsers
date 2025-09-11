package com.stanleymesa.search_data.datasource.remote

import com.stanleymesa.search_domain.model.UserPayload
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val remoteService: SearchRemoteService
) {
    suspend fun getUserSearch(
        page: Int,
        pageSize: Int,
        payload: UserPayload
    ) = remoteService.getUserSearch(
        page = page,
        pageSize = pageSize,
        search = payload.search.ifBlank { null }
    )

}