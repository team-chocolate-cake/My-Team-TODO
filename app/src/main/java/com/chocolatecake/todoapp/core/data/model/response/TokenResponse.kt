package com.chocolatecake.todoapp.core.data.model.response

data class TokenResponse(
    val token: String,
    val expireAt: String
)