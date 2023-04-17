package com.chocolatecake.todoapp.core.data.model.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token") val token: String,
    @SerializedName("expireAt") val expireAt: String,
)