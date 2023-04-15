package com.chocolatecake.todoapp.data.model.response.base

data class BaseResponse<T>(
    val value: T?,
    val message : String?,
    val isSuccess : Boolean
)