package com.chocolatecake.todoapp.ui.login

import com.chocolatecake.todoapp.data.model.response.LoginResponse

interface LoginView {
    fun onFailure(message :String?)
    fun onSuccessLogin()
}