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
                username.length <VALIDATION_USERNAME_LENGTH -> return context.getString(
                    R.string.error_validation_user_name_should_grater_the_limit
                )
            }
        }
        return ""
    }

    private fun Char.isSpace(): Boolean {
        return this == ' '
    }

    private fun Char.isSpecial(): Boolean {
        val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
        return specialChars.contains(this)
    }

    companion object {
        const val VALIDATION_PASSWORD_LENGTH = 8
        const val VALIDATION_USERNAME_LENGTH = 4
    }
}