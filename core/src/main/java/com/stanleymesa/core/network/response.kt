package com.stanleymesa.core.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "code")
    val code: Int? = null,
    @Json(name = "error")
    val error: Boolean = false,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "data")
    val data: T? = null,
)

@JsonClass(generateAdapter = true)
data class BaseListResponse<T>(
    @Json(name = "code")
    val code: Int? = null,
    @Json(name = "error")
    val error: Boolean = false,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "data")
    val data: List<T>? = null,
    @Json(name = "arrayCount")
    val arrayCount: Int? = null,
    @Json(name = "totalItems")
    val totalItems: Int? = null,
    @Json(name = "pageSize")
    val pageSize: Int? = null,
    @Json(name = "isHasNextPage")
    val isHasNextPage: Boolean? = null,
)

data class RefreshToken(
    val access: String?
)