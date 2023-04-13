package com.chocolatecake.todoapp.ui.register

import com.chocolatecake.todoapp.data.model.response.RegisterResponse


interface IRegisterView {
    fun onSuccess(response: RegisterResponse)
    fun onFailure(message: String?)
}