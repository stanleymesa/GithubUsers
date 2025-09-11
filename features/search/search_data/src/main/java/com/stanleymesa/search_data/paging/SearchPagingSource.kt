package com.stanleymesa.search_data.paging

import com.stanleymesa.core.base.BasePagingSource
import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.core.util.extentions.ifNullOrEmpty
import com.stanleymesa.search_domain.model.UserPayload
import com.stanleymesa.search_domain.repository.SearchRepository
import kotlinx.coroutines.flow.collectLatest

class SearchPagingSource(
    private val payload: UserPayload,
    private val repository: SearchRepository,
    private val networkHelper: NetworkHelper
) : BasePagingSource<User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            var data = emptyList<User>()
            var next = true
            val pageSize = params.loadSize
            val pageNum = params.key ?: 1
            var throwable = Throwable()

            val remote = repository.getUserSearch(
                page = pageNum,
                pageSize = pageSize,
                payload = payload
            )

            remote.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        data = result.data.orEmpty()
                        next = result.data?.size == pageSize
                    }

                    is Resource.Empty -> {
                        data = emptyList()
                        next = false
                    }

                    else -> {
                        throwable =
                            if (networkHelper.isNetworkConnected()) Throwable(message = result.message.ifNullOrEmpty { "An error occurred!" }) else Throwable(
                                message = com.stanleymesa.core.R.string.please_check_internet_connection.toString()
                            )
                    }
                }
            }

            if (throwable.message != null) {
                return LoadResult.Error(throwable)
            }

            return LoadResult.Page(
                data = data,
                prevKey = if (pageNum > 1) pageNum - 1 else null,
                nextKey = if (next) pageNum + 1 else null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}