package com.chocolatecake.todoapp.data.model.response

data class TokenResponse(
    val token: String,
    val expireAt: String
)