package com.chocolatecake.todoapp.core.data.model.response

import com.google.gson.annotations.SerializedName

data class RegisterValue(
    @SerializedName("userId") val userId: String,
    @SerializedName("username") val userName: String
)