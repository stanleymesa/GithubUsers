package com.stanleymesa.core.interceptor

import com.stanleymesa.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        this.level = HttpLoggingInterceptor.Level.HEADERS
        this.level = HttpLoggingInterceptor.Level.BODY
    } else {
        this.level = HttpLoggingInterceptor.Level.NONE
    }
}

class AuthenticationInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val githubApiToken = BuildConfig.GITHUB_API_TOKEN

        val builder = chain.request().newBuilder().apply {
            addHeader("Accept", "application/vnd.github+json")
            addHeader("X-GitHub-Api-Version", "2022-11-28")
            addHeader("Authorization", "Bearer $githubApiToken")
        }

        return chain.proceed(builder.build())
    }
}
