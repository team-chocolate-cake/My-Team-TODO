package com.chocolatecake.todoapp.ui.register

import com.chocolatecake.todoapp.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.data.model.response.base.BaseResponse
import com.chocolatecake.todoapp.data.model.response.identity.LoginResponse


interface RegisterView {
    fun onRegisterSuccess(response: RegisterResponse)
    fun onRegisterFailure(message: String?)
    fun onLoginSuccess(response: BaseResponse<LoginResponse>)
    fun onFailure(message: String?)
}