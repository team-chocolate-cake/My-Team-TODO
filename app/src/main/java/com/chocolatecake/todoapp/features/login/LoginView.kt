package com.chocolatecake.todoapp.features.login

interface LoginView {
    fun onFailure(message :String?)

    fun onSuccessLogin()

    fun showLoginError()
}