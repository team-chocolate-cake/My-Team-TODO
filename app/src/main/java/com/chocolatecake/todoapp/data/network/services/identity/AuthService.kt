package com.chocolatecake.todoapp.data.network.services.identity

import com.chocolatecake.todoapp.BuildConfig
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils.getUrl
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

class AuthService(
) : BaseService() {
    override val client: OkHttpClient by lazy {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()
    }

    fun login(userRequest: UserRequest,onFailure: (message: String?) -> Unit,
              onSuccess: (body: String?) -> Unit,) {
        val request = Request.Builder()
            .url(getUrl("login"))
            .addHeader(
                "Authorization",
                Credentials.basic(userRequest.username, userRequest.password)
            )
            .build()
        call(request,onFailure,onSuccess)
    }

    fun register(userRequest: UserRequest,onFailure: (message: String?) -> Unit,
                 onSuccess: (body: String?) -> Unit,) {
        val teamId = BuildConfig.API_KEY
        val body = FormBody.Builder()
            .add("username", userRequest.username)
            .add("password", userRequest.password)
            .add("teamId", teamId)
            .build()
        val request = Request.Builder()
            .url(getUrl("signup"))
            .post(body)
            .build()

        call(request,onFailure,onSuccess)
    }
}