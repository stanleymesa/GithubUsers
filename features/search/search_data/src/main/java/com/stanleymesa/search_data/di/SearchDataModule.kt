package com.stanleymesa.search_data.di

import com.squareup.moshi.Moshi
import com.stanleymesa.core.BuildConfig
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.search_data.datasource.remote.SearchRemoteDataSource
import com.stanleymesa.search_data.datasource.remote.SearchRemoteService
import com.stanleymesa.search_data.repository.SearchRepositoryImpl
import com.stanleymesa.search_domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

    @Singleton
    @Provides
    fun provideRemoteService(
        client: OkHttpClient,
        moshi: Moshi
    ): SearchRemoteService = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(SearchRemoteService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: SearchRemoteDataSource,
        networkHelper: NetworkHelper
    ): SearchRepository = SearchRepositoryImpl(
        remoteDataSource,
        networkHelper
    )

}