package com.chocolatecake.todoapp.data.model

data class LoginResponse(
    val value: Pair<String, String>,
    val message : String? ,
    val isSuccess : Boolean
    )