package com.stanleymesa.core.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.lang.reflect.Type

object NetworkExtension {
    fun <T> Throwable.returnErrorResource() = if (this is HttpException) {
        val errorStr = this.response()?.errorBody()?.string()
        if (errorStr != null) {
            try {
                val type: Type = object : TypeToken<BaseResponse<Any>>() {}.type
                val err = Gson().fromJson<BaseResponse<Any>>(
                    errorStr,
                    type
                )
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
                val type: Type = object : TypeToken<BaseResponse<Any>>() {}.type
                val err = Gson().fromJson<BaseResponse<Any>>(
                    errorStr,
                    type
                )
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