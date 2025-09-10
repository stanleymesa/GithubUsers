package com.stanleymesa.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException


object NetworkExtension {
    fun <T> Throwable.returnErrorResource() = if (this is HttpException) {
        val errorStr = this.response()?.errorBody()?.string()
        if (errorStr != null) {
            try {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(BaseResponse::class.java)
                val err = jsonAdapter.fromJson(errorStr) as BaseResponse<*>
                if (this.code() == 401) {
                    Resource.Unauthorized<T>(err.message)
                } else {
                    Resource.Error(err.message, extra = err.data)
                }
            } catch (e: Exception) {
                Resource.Error(this.message)
            }
        } else {
            if (this.code() == 401) {
                Resource.Unauthorized(this.message)
            } else {
                Resource.Error(this.message)
            }
        }
    } else {
        Resource.Error(this.message)
    }

    fun <T> Throwable.returnErrorResourcePaging() = if (this is HttpException) {
        val errorStr = this.response()?.errorBody()?.string()
        if (errorStr != null) {
            try {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(BaseResponse::class.java)
                val err = jsonAdapter.fromJson(errorStr) as BaseResponse<*>
                if (this.code() == 401) {
                    ResourcePaging.Unauthorized<T>(err.message)
                } else {
                    ResourcePaging.Error(err.message)
                }
            } catch (e: Exception) {
                ResourcePaging.Error(this.message)
            }
        } else {
            if (this.code() == 401) {
                ResourcePaging.Unauthorized(this.message)
            } else {
                ResourcePaging.Error(this.message)
            }
        }
    } else {
        ResourcePaging.Error(this.message)
    }

}