package com.chocolatecake.todoapp.login.presenter

import android.content.Context
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.model.response.LoginResponse
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.login.LoginView
import com.google.gson.Gson

class LoginPresenter(
    private val view: LoginView,
    private val context: Context,
) {
    private val authService: AuthService by lazy { AuthService() }
    private val preferences by lazy {
        com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences().also {
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
                val loginResponse = Gson().fromJson(it.body?.string().toString(), LoginResponse::class.java)
                checkSuccessResponse(loginResponse)
            },
        )
    }

    private fun checkSuccessResponse(loginResponse: LoginResponse) {
        if (loginResponse.isSuccess) {
            view.onSuccessLogin()
            preferences.token = loginResponse.value?.token
        } else {
            view.onFailure(loginResponse.message)
        }
    }
}
