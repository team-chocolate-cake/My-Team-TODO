package com.chocolatecake.todoapp.features.login.util


fun String.passwordLength(): Boolean {
    return this.trim().length < 8
}

fun String.usernameLength(): Boolean {
    return this.trim().length < 3
}