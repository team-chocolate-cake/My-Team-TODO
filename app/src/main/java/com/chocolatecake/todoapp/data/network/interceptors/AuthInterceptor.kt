package com.chocolatecake.todoapp.data.network.interceptors

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response

class AuthInterceptor(private val preferences: TaskSharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.token
        return if (token.isNullOrBlank()) {
            chain.proceed(chain.request())
            Response.Builder()
                .code(401)
                .message("Unauthorized")
                .protocol(Protocol.HTTP_1_1)
                .request(chain.request())
                .build()
        } else {
            val authReq = chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(authReq)
        }
    }
}