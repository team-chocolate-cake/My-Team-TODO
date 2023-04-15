package com.chocolatecake.todoapp.core.data.network.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpClient(private val preferences: com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences){
    fun getClient(): OkHttpClient{
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val authInterceptor =
            com.chocolatecake.todoapp.core.data.network.interceptors.AuthInterceptor(preferences)
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logInterceptor)
            .build()
    }
}