package com.chocolatecake.todoapp.ui.login.view

interface LoginView {
    fun onFailure(message :String?)
    fun onSuccessLogin()
}