package com.chocolatecake.todoapp.register.presenter

import android.content.Context
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.register.RegisterView
import com.google.gson.Gson

class RegistrationPresenter(private val registerView: RegisterView, private val context: Context) {
    private val preferences: com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences by lazy {
        com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences().also { it.initPreferences(context) }
    }
    private val authService: AuthService by lazy { AuthService() }

    fun makeRequest(userRequest: com.chocolatecake.todoapp.core.data.model.request.UserRequest) {
        authService.register(userRequest,
            onSuccess = { response ->
                val body = response?.body?.string().toString()
                val registerResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.RegisterResponse::class.java)
                if (registerResponse.isSuccess) {
                    loginUser(userRequest)
                    registerView.onRegisterSuccess(registerResponse)
                } else {
                    registerView.onRegisterFailure(registerResponse.message)
                }
            },
            onFailure = { registerView.onFailure(it) })
    }

    private fun loginUser(userRequest: com.chocolatecake.todoapp.core.data.model.request.UserRequest) {
        authService.login(userRequest,
            onFailure = { message ->
                registerView.onFailure(message)
            },
            onSuccess = { loginSuccess ->
                val loginBody = loginSuccess.body?.string().toString()
                val loginResponse =
                    Gson().fromJson(loginBody, com.chocolatecake.todoapp.core.data.model.response.LoginResponse::class.java)
                preferences.token = loginResponse.value?.token
                registerView.onLoginSuccess(loginResponse)
            })
    }

}