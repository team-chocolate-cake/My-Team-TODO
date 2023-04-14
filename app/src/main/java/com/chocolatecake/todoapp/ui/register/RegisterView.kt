package com.chocolatecake.todoapp.ui.register

import com.chocolatecake.todoapp.data.model.response.LoginResponse
import com.chocolatecake.todoapp.data.model.response.RegisterResponse


interface RegisterView {
    fun onRegisterSuccess(response: RegisterResponse)
    fun onRegisterFailure(message: String?)
    fun onLoginSuccess(response: LoginResponse)
    fun onFailure(message: String?)
}