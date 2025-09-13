package com.stanleymesa.detail_data.repository

import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.mapper.UserMapper.toDomainModel
import com.stanleymesa.core.shared_data.mapper.UserMapper.toEntity
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.util.DateTimeHelper
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.detail_data.datasource.local.DetailLocalDataSource
import com.stanleymesa.detail_data.datasource.remote.DetailRemoteDataSource
import com.stanleymesa.detail_data.mapper.UserReposMapper.toDomainModel
import com.stanleymesa.detail_data.mapper.UserReposMapper.toEntity
import com.stanleymesa.detail_domain.model.UserRepos
import com.stanleymesa.detail_domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailRemoteDataSource,
    private val localDataSource: DetailLocalDataSource,
    private val networkHelper: NetworkHelper
) : DetailRepository {

    override suspend fun getUser(username: String): Flow<Resource<User>> = flow {
        runCatching {
            val response = remoteDataSource.getUserDetail(username)

            if (response.isSuccessful) {
                val data = response.body()
                var user: User? = null

                /** Upsert user to local database */
                if (data != null) {
                    localDataSource.transaction {
                        localDataSource.upsertUser(data.toEntity())
                        user = localDataSource.getUser(username)?.toDomainModel()
                    }
                }

                /** Return user from local database*/
                user?.let { emit(Resource.Success(it)) }
                if (user == null) {
                    emit(Resource.Error(msg = "User not found"))
                }
            }

        }.getOrElse {
            val user = localDataSource.getUser(username)?.toDomainModel()
            emit(Resource.Error(msg = it.message, data = user))
        }
    }

    override suspend fun getUserRepos(username: String): Flow<Resource<List<UserRepos>>> = flow {
        runCatching {
            val response = remoteDataSource.getUserRepos(username)

            if (response.isSuccessful) {
                val data = response.body()
                var userRepos: List<UserRepos> = emptyList()

                /** Upsert user repos to local database */
                if (data != null) {
                    localDataSource.transaction {
                        localDataSource.upsertAllUserRepos(data.map { it.toEntity() })
                        userRepos =
                            localDataSource.getUserRepos(username).map { it.toDomainModel() }
                                .sortedByDescending {
                                    DateTimeHelper.parse(
                                        it.updatedAt,
                                        DateTimeHelper.FORMAT_yyyy_MM_dd_T_HHmmssZ
                                    ).time
                                }
                    }
                }

                /** Return user repos from local database*/
                emit(Resource.Success(userRepos))
            }

        }.getOrElse {
            val userRepos =
                localDataSource.getUserRepos(username).map { repo -> repo.toDomainModel() }
                    .sortedByDescending { repo ->
                        DateTimeHelper.parse(
                            repo.updatedAt,
                            DateTimeHelper.FORMAT_yyyy_MM_dd_T_HHmmssZ
                        ).time
                    }
            emit(Resource.Error(msg = it.message, data = userRepos))
        }
    }
}