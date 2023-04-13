package com.chocolatecake.todoapp.util

import com.chocolatecake.todoapp.ui.register.RegistrationFragment
import java.lang.Character.isDigit

fun getUsernameStatus(username : String): String {
    for(i in username.trim().indices){
        when {
            isSpecial(username[i]) -> return RegistrationFragment.ERROR_VALIDATION_USER_NAME_SPECIAL
            isSpace(username[i]) -> return RegistrationFragment.ERROR_VALIDATION_USER_NAME_SPACE
            isDigit(username[0]) -> return RegistrationFragment.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
            username.length < RegistrationFragment.VALIDATION_USERNAME_LENGTH -> return RegistrationFragment.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
        }
    }
    return ""
}
fun isSpace(char : Char) = char == ' '
fun isSpecial(char: Char): Boolean {
    val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
    return specialChars.contains(char)
}
