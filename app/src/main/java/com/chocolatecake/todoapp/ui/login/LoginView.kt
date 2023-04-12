package com.chocolatecake.todoapp.ui.login

import com.chocolatecake.todoapp.data.model.response.LoginResponse

interface LoginView {
    fun onFailureLogin(message :String?)
    fun onSuccessLogin()
    fun onSuccessResponse(message: String?)
}