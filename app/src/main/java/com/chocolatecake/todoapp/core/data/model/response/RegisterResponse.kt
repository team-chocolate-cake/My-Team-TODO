package com.chocolatecake.todoapp.core.data.model.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("value") val registerValue: com.chocolatecake.todoapp.core.data.model.response.RegisterValue,
    val message: String?,
    val isSuccess: Boolean
)