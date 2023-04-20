package com.chocolatecake.todoapp.features.login.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.model.response.TokenResponse
import com.chocolatecake.todoapp.core.data.model.response.BaseResponse
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.features.login.util.Validation
import com.chocolatecake.todoapp.login.LoginView

class LoginPresenter(
    private val view: LoginView,
    private val preferences: TaskSharedPreferences,
) {
    private val authService: AuthService by lazy { AuthService() }
    private val validation by lazy { Validation() }

    fun login(userRequest: UserRequest) {
        if (validation.isNotEmpty(userRequest)) {
            if (validation.isValidate(userRequest)) {
                view.showProgressBar(visible = true)
                authService.login(
                    userRequest = userRequest,
                    onFailure = ::onFailure,
                ) {
                    checkSuccessResponse(it)
                }
            }
        } else {
            view.showLoginFailedError("please fill fields")
        }
    }


    fun checkUsernameValidate(username: String) {
        if (validation.isValidateUsername(username)) {
            view.showUsernameValidationFailedError(visible = true)
        } else {
            view.showUsernameValidationFailedError(visible = false)
        }
    }

    fun checkPasswordValidate(password: String) {
        if (validation.isValidatePassword(password)) {
            view.showPasswordValidationFailedError(visible = true)
        } else {
            view.showPasswordValidationFailedError(visible = false)
        }
    }


    private fun checkSuccessResponse(loginResponse: BaseResponse<TokenResponse>) {
        if (loginResponse.isSuccess) {
            view.navigateToHomeScreen()
            preferences.token = loginResponse.value?.token
        } else {
            view.showLoginFailedError(loginResponse.message)
            view.showProgressBar(visible = false)
        }
    }

    private fun onFailure(message: String?, statusCode: Int) {
        view.showLoginFailedError(message)
        view.showProgressBar(visible = false)
    }
}
