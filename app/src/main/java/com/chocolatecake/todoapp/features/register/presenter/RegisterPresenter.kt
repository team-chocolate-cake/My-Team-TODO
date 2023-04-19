package com.chocolatecake.todoapp.features.register.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.features.register.RegisterView

class RegisterPresenter(
    private val view: RegisterView,
    private val preferences: TaskSharedPreferences
) {
    private val authService: AuthService by lazy { AuthService() }

    fun makeRequest(userRequest: UserRequest) {
        authService.register(userRequest,
            onSuccess = { response ->
                if (response.isSuccess) {
                    response.value?.let { view.onRegisterSuccess(it) }
                    loginUser(userRequest)
                } else {
                    view.onRegisterFailure(response.message)
                }
            },
            onFailure = { message: String?, statusCode: Int -> view.onFailure(message) }
        )
    }

    private fun loginUser(userRequest: UserRequest) {
        authService.login(userRequest,
            onFailure = { message: String?, statusCode: Int -> view.onFailure(message) }
        ) { response ->
            preferences.token = response.value?.token
            view.onLoginSuccess()
        }
    }
}