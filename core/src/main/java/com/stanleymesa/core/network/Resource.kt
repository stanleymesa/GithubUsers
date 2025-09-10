package com.stanleymesa.core.network

import com.stanleymesa.core.enums.NetworkState

sealed class Resource<out T> constructor(
    val status: NetworkState,
    val message: String? = null,
    val data: T? = null,
) {

    class Success<T>(data: T, message: String? = null) : Resource<T>(
        data = data,
        status = NetworkState.SUCCESS,
        message = message
    )

    data class Error<T>(val msg: String?, val extra: Any? = null) : Resource<T>(
        status = NetworkState.ERROR,
        message = if (msg?.contains("No address associated with hostname") == true) "Terjadi Kesalahan" else msg
    )

    class Unauthorized<T>(message: String?) : Resource<T>(
        status = NetworkState.UNAUTHORIZED, message = message
    )

    class Empty<T> : Resource<T>(status = NetworkState.EMPTY)

}

sealed class ResourcePaging<out T> constructor(
    val status: NetworkState,
    val message: String? = null,
    val data: T? = null,
    val arrayCount: Int? = null,
    val totalItems: Int? = null,
    val pageSize: Int? = null,
    val isHasNextPage: Boolean? = false,
) {

    class Success<T>(
        data: T,
        arrayCount: Int? = null,
        totalItems: Int? = null,
        pageSize: Int? = null,
        isHasNextPage: Boolean? = false,
        message: String? = null
    ) : ResourcePaging<T>(
        data = data,
        arrayCount = arrayCount,
        totalItems = totalItems,
        pageSize = pageSize,
        isHasNextPage = isHasNextPage,
        message = message,
        status = NetworkState.SUCCESS,
    )

    class Error<T>(message: String?) : ResourcePaging<T>(
        status = NetworkState.ERROR,
        message = if (message?.contains("No address associated with hostname") == true) "Terjadi Kesalahan" else message
    )

    class Unauthorized<T>(message: String?) : ResourcePaging<T>(
        status = NetworkState.UNAUTHORIZED, message = message
    )

    class Empty<T> : ResourcePaging<T>(status = NetworkState.EMPTY)

}