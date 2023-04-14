package com.chocolatecake.todoapp.ui.register.presenter

import android.content.Context
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.LoginResponse
import com.chocolatecake.todoapp.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.ui.register.RegisterView
import com.google.gson.Gson

class RegistrationPresenter(private val registerView: RegisterView, private val context: Context) {
    private val preferences: TaskSharedPreferences by lazy {
        TaskSharedPreferences().also { it.initPreferences(context) }
    }
    private val authService: AuthService by lazy { AuthService() }

    fun makeRequest(userRequest: UserRequest) {
        authService.register(userRequest,
            onSuccess = { response ->
                val body = response?.body?.string().toString()
                val registerResponse = Gson().fromJson(body, RegisterResponse::class.java)
                if (registerResponse.isSuccess) {
                    loginUser(userRequest)
                    registerView.onRegisterSuccess(registerResponse)
                } else {
                    registerView.onRegisterFailure(registerResponse.message)
                }
            },
            onFailure = { registerView.onFailure(it) })
    }

    private fun loginUser(userRequest: UserRequest) {
        authService.login(userRequest,
            onFailure = { message ->
                registerView.onFailure(message)
            },
            onSuccess = { loginSuccess ->
                val loginBody = loginSuccess.body?.string().toString()
                val loginResponse =
                    Gson().fromJson(loginBody, LoginResponse::class.java)
                preferences.token = loginResponse.value?.token
                registerView.onLoginSuccess(loginResponse)
            })
    }

}