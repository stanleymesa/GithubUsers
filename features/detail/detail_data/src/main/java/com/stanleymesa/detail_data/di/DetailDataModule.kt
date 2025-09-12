package com.stanleymesa.detail_data.di

import com.squareup.moshi.Moshi
import com.stanleymesa.core.BuildConfig
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.detail_data.datasource.local.DetailLocalDataSource
import com.stanleymesa.detail_data.datasource.remote.DetailRemoteDataSource
import com.stanleymesa.detail_data.datasource.remote.DetailRemoteService
import com.stanleymesa.detail_data.repository.DetailRepositoryImpl
import com.stanleymesa.detail_domain.repository.DetailRepository
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
object DetailDataModule {

    @Singleton
    @Provides
    fun provideRemoteService(
        client: OkHttpClient,
        moshi: Moshi
    ): DetailRemoteService = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(DetailRemoteService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: DetailRemoteDataSource,
        localDataSource: DetailLocalDataSource,
        networkHelper: NetworkHelper
    ): DetailRepository = DetailRepositoryImpl(
        remoteDataSource,
        localDataSource,
        networkHelper
    )

}