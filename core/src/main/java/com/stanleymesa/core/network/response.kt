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
    @Json(name = "total_count")
    val totalCount: Int? = null,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean = false,
    @Json(name = "items")
    val items: List<T>? = null,
)

