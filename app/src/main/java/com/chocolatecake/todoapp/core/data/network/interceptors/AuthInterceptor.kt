package com.chocolatecake.todoapp.core.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.token
        return if (token.isNullOrBlank()) {
            chain.proceed(chain.request())
        } else {
            val authReq = chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(authReq)
        }
    }
}