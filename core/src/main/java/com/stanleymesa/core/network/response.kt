package com.stanleymesa.core.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @Expose @SerializedName("code")
    val code: Int? = null,
    @Expose @SerializedName("error")
    val error: Boolean = false,
    @Expose @SerializedName("message")
    val message: String? = null,
    @Expose @SerializedName("data")
    val data: T? = null,
)

data class BaseListResponse<T>(
    @Expose @SerializedName("code")
    val code: Int? = null,
    @Expose @SerializedName("error")
    val error: Boolean = false,
    @Expose @SerializedName("message")
    val message: String? = null,
    @Expose @SerializedName("data")
    val data: List<T>? = null,
    @Expose @SerializedName("arrayCount")
    val arrayCount: Int? = null,
    @Expose @SerializedName("totalItems")
    val totalItems: Int? = null,
    @Expose @SerializedName("pageSize")
    val pageSize: Int? = null,
    @Expose @SerializedName("isHasNextPage")
    val isHasNextPage: Boolean? = null,
)

data class RefreshToken(
    val access: String?
)