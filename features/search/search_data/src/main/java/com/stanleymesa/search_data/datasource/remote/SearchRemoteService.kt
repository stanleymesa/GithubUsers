package com.stanleymesa.search_data.datasource.remote

import com.stanleymesa.core.network.BaseListResponse
import com.stanleymesa.core.network.RemoteEndPoint
import com.stanleymesa.core.shared_data.dto.UserDTO
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface SearchRemoteService {

    @GET(RemoteEndPoint.ENDPOINT_USER_SEARCH)
    suspend fun getUserSearch(
        @Query("q") search: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") pageSize: Int? = null,
    ): BaseListResponse<UserDTO>

}
