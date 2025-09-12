package com.stanleymesa.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stanleymesa.core.constant.AppConstants
import com.stanleymesa.core.constant.AppConstants.APP_DATABASE
import com.stanleymesa.core.database.AppDatabase
import com.stanleymesa.core.database.dao.UserDao
import com.stanleymesa.core.database.dao.UserReposDao
import com.stanleymesa.core.interceptor.AuthenticationInterceptor
import com.stanleymesa.core.interceptor.loggingInterceptor
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.core.util.extentions.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, APP_DATABASE
    ).fallbackToDestructiveMigration(true).build()

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao

    @Provides
    @Singleton
    fun provideUserRepositoryDao(appDatabase: AppDatabase): UserReposDao = appDatabase.userReposDao

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideHttpClient(dataStore: DataStore<Preferences>) =
        OkHttpClient().newBuilder().connectTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(AuthenticationInterceptor())
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true).build()

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper =
        NetworkHelper(context)

}