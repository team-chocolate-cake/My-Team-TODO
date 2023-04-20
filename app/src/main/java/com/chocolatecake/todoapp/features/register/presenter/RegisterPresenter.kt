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
    private val registerValidation = RegisterValidation()
    var isUsernameValid: Boolean = false
    var isPasswordValid: Boolean = false
    var isConfirmPasswordValid: Boolean = false

    fun makeRequest(userRequest: UserRequest) {
        authService.register(userRequest,
            onSuccess = { response ->
                if (response.isSuccess) {
                    loginUser(userRequest)
                } else {
                    view.showErrorRegister(response.message)
                }
            },
            onFailure = { message: String?, _: Int -> view.showNoInternetConnection(message) }
        )
    }

    private fun loginUser(userRequest: UserRequest) {
        authService.login(userRequest,
            onFailure = { message: String?, _: Int -> view.showNoInternetConnection(message) }
        ) { response ->
            preferences.token = response.value?.token
            view.navigateToHome()
        }
    }

    fun checkUsernameText(usernameText: String, requireContext: Context) {
        when (registerValidation.checkUsernameStatus(usernameText, requireContext)) {
            requireContext.getString(R.string.error_validation_user_name_special) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_special)
            )

            requireContext.getString(R.string.error_validation_user_name_space) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_space)
            )

            requireContext.getString(R.string.username_validate) -> setErrorUsername(
                requireContext.getString(R.string.username_validate)
            )

            requireContext.getString(R.string.error_validation_user_name_start_with_digit) -> setErrorUsername(
                requireContext.getString(R.string.error_validation_user_name_start_with_digit)
            )

            else -> {
                view.hideTextValidateUsername()
                isUsernameValid = true
            }
        }
    }

    fun checkPasswordText(passwordLength: Int) {
        isPasswordValid = when {
            registerValidation.checkPasswordLength(passwordLength) -> {
                view.showErrorPasswordLength()
                false
            }

            else -> {
                view.hideValidatePasswordText()
                true
            }
        }
    }

    fun checkConfirmPasswordText(passwordText: String, confirmPasswordText: String) {
        isConfirmPasswordValid = if (registerValidation.checkConfirmPasswordAndPasswordText(
                passwordText,
                confirmPasswordText
            )
        ) {
            view.showConfirmPassword(true)
            true
        } else {
            view.showConfirmPassword(false)
            false
        }
    }

    fun handleRegister(
        isValidateFailed: Boolean,
        usernameText: String,
        passwordText: String,
        confirmPasswordText: String
    ) {
        if (isValidateFailed) {
            view.registerUser()
        }
        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            view.showEmptyValidError()
        }
        if (confirmPasswordText != passwordText) {
            view.showMismatchConfirmPassword()
        }
    }

    fun setErrorUsername(error: String) {
        view.showErrorInvalidUsername(error)
    }

}