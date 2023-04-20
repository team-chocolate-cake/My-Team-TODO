package com.chocolatecake.todoapp.features.login.util

import com.chocolatecake.todoapp.core.data.model.request.UserRequest

class Validation {

    fun isValidateUsername(value: String): Boolean {
        return value.trim().length < 4
    }

    fun isValidatePassword(value: String): Boolean {
        return value.trim().length < 8
    }

    fun isNotEmpty(user: UserRequest): Boolean{
        return user.username.isNotEmpty() && user.password.isNotEmpty()
    }

    fun isValidate(user: UserRequest): Boolean{
        return !isValidateUsername(user.username) && !isValidatePassword(user.password)
    }
}