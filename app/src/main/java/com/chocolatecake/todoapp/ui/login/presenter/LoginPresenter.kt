package com.chocolatecake.todoapp.ui.login.presenter

import android.content.Context
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.base.BaseResponse
import com.chocolatecake.todoapp.data.model.response.identity.LoginResponse
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.ui.login.view.LoginView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class LoginPresenter(
    private val view: LoginView,
    private val context: Context,
) {
    private val authService: AuthService by lazy { AuthService() }
    private val preferences by lazy {
        TaskSharedPreferences().also {
            it.initPreferences(context = context)
        }
    }

    fun clickableLoginButton(userRequest: UserRequest) {
        authService.login(
            userRequest = userRequest,
            onFailure = {
                view.onFailure("Pleas check connection with internet")
            },
            onSuccess = {
                val type: Type = object : TypeToken<BaseResponse<LoginResponse>>() {}.type
                val loginResponse = Gson().fromJson<BaseResponse<LoginResponse>>(it.body?.string().toString(), type)
                checkSuccessResponse(loginResponse)
            },
        )
    }

    private fun checkSuccessResponse(loginResponse: BaseResponse<LoginResponse>) {
        if (loginResponse.isSuccess) {
            view.onSuccessLogin()

            preferences.token = loginResponse.value?.token
        } else {
            view.onFailure(loginResponse.message)
        }
    }
}
