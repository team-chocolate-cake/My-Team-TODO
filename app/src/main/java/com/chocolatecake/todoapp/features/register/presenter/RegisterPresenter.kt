package com.chocolatecake.todoapp.features.register.presenter

import android.content.Context
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.features.register.RegisterView
import com.chocolatecake.todoapp.features.register.util.RegisterValidation


class RegisterPresenter(
    private val view: RegisterView,
    private val preferences: TaskSharedPreferences
) {
    private val authService: AuthService by lazy { AuthService() }

    fun makeRequest(userRequest: UserRequest) {
        authService.register(userRequest,
            onSuccess = { response ->
                if (response.isSuccess) {
                    response.value?.let { view.navigationToHome() }
                    loginUser(userRequest)
                } else {
                    view.showErrorRegister(response.message)
                }
            },
            onFailure = { message: String?, statusCode: Int -> view.showNoInternetConnection(message) }
        )
    }

    private fun loginUser(userRequest: UserRequest) {
        authService.login(userRequest,
            onFailure = { message: String?, statusCode: Int -> view.showNoInternetConnection(message) }
        ) { response ->
            preferences.token = response.value?.token
            view.navigationToHome()
        }
    }

    fun checkUsernameText(usernameText: String, requireContext: Context) {
        when (RegisterValidation().getUsernameStatus(usernameText, requireContext)) {
            requireContext.getString(R.string.error_validation_user_name_special) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_special)
            )

            requireContext.getString(R.string.error_validation_user_name_space) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_space)
            )

            requireContext.getString(R.string.error_validation_user_name_should_grater_the_limit) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_should_grater_the_limit)
            )

            requireContext.getString(R.string.error_validation_user_name_start_with_digit) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_start_with_digit)
            )

            else -> {
                view.hideUsername()
            }
        }
    }

    fun checkPasswordText(passwordLength: Int) {
        when {
            passwordLength < VALIDATION_PASSWORD_LENGTH -> {
                view.showErrorPasswordLength()
            }

            else -> {
                view.hideValidatePasswordText()
            }
        }
    }

    fun checkConfirmPasswordText(passwordText: String, confirmPasswordText: String) {
        if (passwordText == confirmPasswordText) {
            view.showConfirmPassword(true)
        } else {
            view.showConfirmPassword(false)
        }
    }

    fun handleRegister(isValidateFailed: Boolean,usernameText: String,passwordText: String,confirmPasswordText: String){
        if (isValidateFailed) {
            view.registerUser()
        }
        if (usernameText.isEmpty()) {
            view.showEmptyUsernameError()
        }
        if (passwordText.isEmpty()) {
            view.showEmptyPasswordError()
        }
        if (confirmPasswordText != passwordText) {
            view.showMismatchConfirmPassword()
        }
    }

    fun setErrorUsername(error: String) {
        view.showErrorInvalidUsername(error)
    }

    companion object {
        const val VALIDATION_PASSWORD_LENGTH = 8
        const val VALIDATION_USERNAME_LENGTH = 4
    }
}