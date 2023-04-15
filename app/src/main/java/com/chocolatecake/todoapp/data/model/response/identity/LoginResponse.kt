package com.chocolatecake.todoapp.data.model.response.identity

data class LoginResponse(
    val token: String,
    val expireAt: String
)