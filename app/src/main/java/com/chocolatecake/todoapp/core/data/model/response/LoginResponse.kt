package com.chocolatecake.todoapp.core.data.model.response

data class LoginResponse(
    val value: TokenResponse?,
    val message : String?,
    val isSuccess : Boolean
)