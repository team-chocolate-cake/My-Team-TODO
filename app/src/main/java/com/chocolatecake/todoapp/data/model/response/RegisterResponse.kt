package com.chocolatecake.todoapp.data.model.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("value") val registerValue: RegisterValue,
    val userId: String,
    val username: String,
)