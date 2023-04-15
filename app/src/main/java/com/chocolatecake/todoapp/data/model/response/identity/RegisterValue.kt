package com.chocolatecake.todoapp.data.model.response.identity

import com.google.gson.annotations.SerializedName

data class RegisterValue(
    @SerializedName("userId") val userId: String,
    @SerializedName("username") val userName: String
)