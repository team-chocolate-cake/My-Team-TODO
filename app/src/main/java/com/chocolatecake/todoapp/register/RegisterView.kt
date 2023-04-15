package com.chocolatecake.todoapp.register

import com.chocolatecake.todoapp.core.data.model.response.LoginResponse
import com.chocolatecake.todoapp.core.data.model.response.RegisterResponse


interface RegisterView {
    fun onRegisterSuccess(response: RegisterResponse)
    fun onRegisterFailure(message: String?)
    fun onLoginSuccess(response: LoginResponse)
    fun onFailure(message: String?)
}