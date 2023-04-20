package com.chocolatecake.todoapp.features.register.util

data class ValidationState(
    var isUsernameValid: Boolean = false,
    var isPasswordValid: Boolean = false,
    var isConfirmPasswordValid: Boolean = false
)
