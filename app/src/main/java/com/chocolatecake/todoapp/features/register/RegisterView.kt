package com.chocolatecake.todoapp.features.register

interface RegisterView {
    fun navigationToHome()
    fun showErrorRegister(message: String?)

    fun showNoInternetConnection(message: String?)

    fun hideUsername()

    fun showErrorInvalidUsername(message: String?)

    fun showErrorPasswordLength()

    fun hideValidatePasswordText()

    fun showConfirmPassword(isVisible: Boolean)

    fun registerUser()

    fun showEmptyUsernameError()

    fun showEmptyPasswordError()

    fun showMismatchConfirmPassword()

}