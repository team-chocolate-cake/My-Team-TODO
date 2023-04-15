package com.chocolatecake.todoapp.register


interface RegisterView {
    fun onRegisterSuccess(response: com.chocolatecake.todoapp.core.data.model.response.RegisterResponse)
    fun onRegisterFailure(message: String?)
    fun onLoginSuccess(response: com.chocolatecake.todoapp.core.data.model.response.LoginResponse)
    fun onFailure(message: String?)
}