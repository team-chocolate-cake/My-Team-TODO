package com.chocolatecake.todoapp.util

import android.content.Context
import android.view.View
import android.widget.TextView
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.ui.register.RegistrationFragment
import java.lang.Character.isDigit

fun getUsernameStatus(username : String,context : Context): String {
    for(i in username.trim().indices){
        when {

            isSpecial(username[i]) -> return context.getString(R.string.error_validation_user_name_special)
            isSpace(username[i]) -> return  context.getString(R.string.error_validation_user_name_space)
            isDigit(username[0]) -> return context.getString(R.string.error_validation_user_name_should_grater_the_limit)
            username.length < RegistrationFragment.VALIDATION_USERNAME_LENGTH -> return context.getString(R.string.error_validation_user_name_start_with_digit)

        }
    }
    return ""
}
fun isSpace(char : Char) = char == ' '
fun isSpecial(char: Char): Boolean {
    val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
    return specialChars.contains(char)
}