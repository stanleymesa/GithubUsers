package com.stanleymesa.core.interceptor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.stanleymesa.core.BuildConfig
import com.stanleymesa.core.datastore.AppDataStoreKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection

val loggingInterceptor = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        this.level = HttpLoggingInterceptor.Level.HEADERS
        this.level = HttpLoggingInterceptor.Level.BODY
    } else {
        this.level = HttpLoggingInterceptor.Level.NONE
    }
}

class AuthenticationInterceptor(private val dataStore: DataStore<Preferences>) : Interceptor {

    var token = ""
    var isLoggedIn = false

    private fun fetchDataStore() {
        runBlocking {
            token = dataStore.data.first()[AppDataStoreKeys.KEY_TOKEN] ?: ""
            isLoggedIn = dataStore.data.first()[AppDataStoreKeys.KEY_IS_LOGGED_IN] ?: false
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        fetchDataStore()
        val builder = chain.request().newBuilder()
        if (isLoggedIn) {
            builder.addHeader("auth-token", token)
        }
        return chain.proceed(builder.build())
    }
}

class TokenExpiredInterceptor(private val dataStore: DataStore<Preferences>) : Interceptor {
    var isLoggedIn = false
    private fun fetchDataStore() {
        runBlocking {
            isLoggedIn = dataStore.data.first()[AppDataStoreKeys.KEY_IS_LOGGED_IN] ?: false
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        fetchDataStore()
        val request = chain.request().newBuilder().build()
        val response = chain.proceed(request)
        if (isLoggedIn && response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            CoroutineScope(Dispatchers.IO).launch {
                dataStore.edit { prefs ->
                    prefs.remove(AppDataStoreKeys.KEY_IS_LOGGED_IN)
                    prefs.remove(AppDataStoreKeys.KEY_TOKEN)
                    prefs[AppDataStoreKeys.KEY_IS_FORCE_LOGOUT] = true
                }
            }
        }
        return response
    }
}