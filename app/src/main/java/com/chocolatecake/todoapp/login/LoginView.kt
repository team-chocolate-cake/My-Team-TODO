package com.chocolatecake.todoapp.login

interface LoginView {
    fun onFailure(message :String?)
    fun onSuccessLogin()
    fun showLoginError()
}