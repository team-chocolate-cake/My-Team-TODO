package com.chocolatecake.todoapp.register

import com.chocolatecake.todoapp.core.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.core.data.model.response.TokenResponse


interface RegisterView {
    fun onRegisterSuccess(response: RegisterResponse)
    fun onRegisterFailure(message: String?)
    fun onLoginSuccess()
    fun onFailure(message: String?)
}