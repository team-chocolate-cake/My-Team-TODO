package com.chocolatecake.todoapp.features.register

import com.chocolatecake.todoapp.core.data.model.response.RegisterResponse

interface RegisterView {
    fun onRegisterSuccess(response: RegisterResponse)
    fun onRegisterFailure(message: String?)
    fun onLoginSuccess()
    fun onFailure(message: String?)
}