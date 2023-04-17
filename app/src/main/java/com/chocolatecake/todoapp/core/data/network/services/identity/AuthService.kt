package com.chocolatecake.todoapp.core.data.network.services.identity

import com.chocolatecake.todoapp.BuildConfig
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.core.data.model.response.TokenResponse
import com.chocolatecake.todoapp.core.data.model.response.base.BaseResponse
import com.chocolatecake.todoapp.core.data.network.services.base.BaseService
import com.chocolatecake.todoapp.core.data.network.services.utils.Constants.LOGIN_ENDPOINT
import com.chocolatecake.todoapp.core.data.network.services.utils.Constants.SIGNUP_ENDPOINT
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

class AuthService : BaseService() {
    override val client: OkHttpClient by lazy {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()
    }

    fun login(
        userRequest: UserRequest, onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<TokenResponse>) -> Unit,
    ) {
        val request = Request.Builder()
            .url(LOGIN_ENDPOINT)
            .addHeader(
                "Authorization",
                Credentials.basic(userRequest.username, userRequest.password)
            )
            .build()
        makeRequest(request, onFailure, onSuccess)
    }

    fun register(
        userRequest: UserRequest, onFailure: (message: String? , statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<RegisterResponse>) -> Unit,
    ) {
        val teamId = BuildConfig.API_KEY
        val body = FormBody.Builder()
            .add("username", userRequest.username)
            .add("password", userRequest.password)
            .add("teamId", teamId)
            .build()
        val request = Request.Builder()
            .url(SIGNUP_ENDPOINT)
            .post(body)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }
}