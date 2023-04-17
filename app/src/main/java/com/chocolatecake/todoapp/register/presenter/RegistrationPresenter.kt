package com.chocolatecake.todoapp.register.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.register.RegisterView

class RegistrationPresenter(
    private val registerView: RegisterView,
    private val preferences: TaskSharedPreferences
) {

    private val authService: AuthService by lazy { AuthService() }

    fun makeRequest(userRequest: UserRequest) {
        authService.register(userRequest,
            onSuccess = { response ->
                if (response.isSuccess) {
                    loginUser(userRequest)
                    response.value?.let { registerView.onRegisterSuccess(it) }
                } else {
                    registerView.onRegisterFailure(response.message)
                }
            },
            onFailure = { message: String?, statusCode: Int -> registerView.onFailure(message) }
        )
    }

    private fun loginUser(userRequest: UserRequest) {
        authService.login(userRequest,
            onFailure = { message: String?, statusCode: Int -> registerView.onFailure(message) }
        ) { response ->
            preferences.token = response.value?.token
            registerView.onLoginSuccess()
        }
    }
}