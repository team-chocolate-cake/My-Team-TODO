package com.chocolatecake.todoapp.data.model.response

data class LoginResponse(
    val value: TokenResponse?,
    val message : String?,
    val isSuccess : Boolean
)