package com.stanleymesa.detail_data.datasource.remote

import com.stanleymesa.core.network.RemoteEndPoint
import com.stanleymesa.core.shared_data.dto.UserDTO
import com.stanleymesa.detail_data.model.UserReposDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@JvmSuppressWildcards
interface DetailRemoteService {

    @GET(RemoteEndPoint.ENDPOINT_USER_DETAIL)
    suspend fun getUserDetail(
        @Path("username") username: String,
    ): Response<UserDTO>

    @GET(RemoteEndPoint.ENDPOINT_USER_REPO)
    suspend fun getUserRepos(
        @Path("username") username: String,
    ): Response<List<UserReposDTO>>

}
