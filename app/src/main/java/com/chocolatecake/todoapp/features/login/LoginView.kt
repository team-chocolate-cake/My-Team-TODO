package com.chocolatecake.todoapp.features.login

interface LoginView {
    fun navigateToHomeScreen()
    fun showLoginFailedError(message: String?)
    fun showUsernameValidationFailedError(visible: Boolean)
    fun showPasswordValidationFailedError(visible: Boolean)
    fun showProgressBar(visible: Boolean)

}