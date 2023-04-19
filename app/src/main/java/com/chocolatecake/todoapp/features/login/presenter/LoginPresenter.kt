package com.chocolatecake.todoapp.features.login.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.model.response.TokenResponse
import com.chocolatecake.todoapp.core.data.model.response.BaseResponse
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.features.login.LoginView

class LoginPresenter(
    private val view: LoginView,
    private val preferences: TaskSharedPreferences,
) {
    private val authService: AuthService by lazy { AuthService() }

    fun login(userRequest: UserRequest) {
        authService.login(
            userRequest = userRequest,
            onFailure = ::onFailure,
        ) {
            checkSuccessResponse(it)
        }
    }

    private fun checkSuccessResponse(loginResponse: BaseResponse<TokenResponse>) {
        if (loginResponse.isSuccess) {
            view.onSuccessLogin()
            preferences.token = loginResponse.value?.token
        } else {
            view.onFailure(loginResponse.message)
        }
    }

    private fun onFailure(message: String?, statusCode: Int,){
        view.onFailure(message)
    }
}
