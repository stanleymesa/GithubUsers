package com.stanleymesa.core.database.converter

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stanleymesa.core.database.entity.UserReposEntity


class Converters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val listStringAdapter: JsonAdapter<List<String>> = moshi.adapter(
        Types.newParameterizedType(List::class.java, String::class.java)
    )

    @TypeConverter
    fun listStringToString(data: List<String>): String {
        return listStringAdapter.toJson(data)
    }

    @TypeConverter
    fun stringToListString(string: String): List<String> {
        if (string.isBlank()) {
            return emptyList()
        }
        return listStringAdapter.fromJson(string) ?: emptyList()
    }

    @TypeConverter
    fun ownerToString(data: UserReposEntity.Owner): String {
        val adapter = moshi.adapter(UserReposEntity.Owner::class.java)
        return adapter.toJson(data)
    }

    @TypeConverter
    fun stringToOwner(string: String): UserReposEntity.Owner {
        val adapter = moshi.adapter(UserReposEntity.Owner::class.java)
        return adapter.fromJson(string) ?: UserReposEntity.Owner.empty()
    }

}