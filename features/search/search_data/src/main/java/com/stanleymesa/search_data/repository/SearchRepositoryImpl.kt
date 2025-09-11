package com.stanleymesa.search_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.mapper.UserMapper.toDomainModel
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.core.util.extentions.isFalse
import com.stanleymesa.search_data.datasource.remote.SearchRemoteDataSource
import com.stanleymesa.search_data.paging.SearchPagingSource
import com.stanleymesa.search_domain.model.UserPayload
import com.stanleymesa.search_domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val networkHelper: NetworkHelper
) : SearchRepository {

    override suspend fun getUserSearch(
        page: Int,
        pageSize: Int,
        payload: UserPayload
    ): Flow<Resource<List<User>>> = flow {
        runCatching {
            if (payload.search.isEmpty()) {
                emit(Resource.Empty())
                return@flow
            }

            val response = remoteDataSource.getUserSearch(page, pageSize, payload)
            if (response.items.isNullOrEmpty().isFalse()) {
                emit(Resource.Success(response.items?.map { it.toDomainModel() }.orEmpty()))
            } else emit(Resource.Empty())

        }.getOrElse {
            emit(Resource.Error(it.message))
        }
    }

    override suspend fun getUserSearchPaging(payload: UserPayload): Flow<PagingData<User>> =
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2, initialLoadSize = 10),
            pagingSourceFactory = {
                SearchPagingSource(
                    payload = payload,
                    repository = this,
                    networkHelper = networkHelper
                )
            }
        ).flow
}