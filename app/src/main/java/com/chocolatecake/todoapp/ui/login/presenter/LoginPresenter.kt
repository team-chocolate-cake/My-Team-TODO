package com.chocolatecake.todoapp.ui.login.presenter

import android.content.Context
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.LoginResponse
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.ui.login.LoginView
import com.google.gson.Gson

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
                val auth = it.body?.string().toString()
                val loginResponse = Gson().fromJson(auth, LoginResponse::class.java)
                checkSuccessResponse(loginResponse)
            },
        )
    }

    private fun checkSuccessResponse(loginResponse: LoginResponse) {
        if (loginResponse.isSuccess) {
            view.onSuccessLogin()

            preferences.apply {
                initPreferences(context = context)
                token = loginResponse.value?.token
            }
        } else {
            view.onFailure(loginResponse.message)
        }
    }
}
