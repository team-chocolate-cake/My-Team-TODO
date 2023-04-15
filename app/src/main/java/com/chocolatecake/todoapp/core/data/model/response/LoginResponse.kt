package com.chocolatecake.todoapp.core.data.model.response

data class LoginResponse(
    val value: com.chocolatecake.todoapp.core.data.model.response.TokenResponse?,
    val message : String?,
    val isSuccess : Boolean
)