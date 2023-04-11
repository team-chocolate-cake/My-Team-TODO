package com.chocolatecake.todoapp.data.model.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    val userId: String,
    @SerializedName(value = "username") val userName: String,
)