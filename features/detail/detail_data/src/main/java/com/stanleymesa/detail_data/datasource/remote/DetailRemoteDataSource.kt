package com.stanleymesa.detail_data.datasource.remote

import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val remoteService: DetailRemoteService
) {
    suspend fun getUserDetail(username: String) = remoteService.getUserDetail(username)
    suspend fun getUserRepos(username: String) = remoteService.getUserRepos(username)

}