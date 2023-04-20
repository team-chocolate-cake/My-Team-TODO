package com.chocolatecake.todoapp.features.register

interface RegisterView {
    fun navigateToHome()
    fun showErrorRegister(message: String?)

    fun showNoInternetConnection(message: String?)

    fun hideTextValidateUsername()

    fun showErrorInvalidUsername(message: String?)

    fun showErrorPasswordLength()

    fun hideValidatePasswordText()

    fun showConfirmPassword(isVisible: Boolean)

    fun registerUser()

    fun showEmptyValidError()

    fun showMismatchConfirmPassword()

}