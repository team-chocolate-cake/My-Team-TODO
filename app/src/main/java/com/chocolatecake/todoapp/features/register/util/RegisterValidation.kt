package com.chocolatecake.todoapp.features.register.util

import android.content.Context
import com.chocolatecake.todoapp.R
import java.lang.Character.isDigit

class RegisterValidation {
    fun getUsernameStatus(username: String, context: Context): String {
        for (i in username.trim().indices) {
            when {
                username[i].isSpecial()-> return context.getString(R.string.error_validation_user_name_special)
                username[i].isSpace() -> return context.getString(R.string.error_validation_user_name_space)
                isDigit(username[0]) -> return context.getString(R.string.error_validation_user_name_start_with_digit)
                username.length <VALIDATION_USERNAME_LENGTH -> return context.getString(R.string.username_validate)
            }
        }
        return ""
    }

    fun checkPasswordLength(passwordLength: Int): Boolean{
        return passwordLength < 8
    }

    fun checkConfirmPasswordAndPasswordText(passwordText: String, confirmPasswordText: String): Boolean{
        return passwordText == confirmPasswordText
    }
    private fun Char.isSpace(): Boolean {
        return this == ' '
    }

    private fun Char.isSpecial(): Boolean {
        val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
        return specialChars.contains(this)
    }

    companion object {
        const val VALIDATION_USERNAME_LENGTH = 4
    }
}