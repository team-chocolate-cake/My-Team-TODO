package com.chocolatecake.todoapp.util

fun isUsernameValid(username: String): String {
    for(i in username.trim().indices){
        if(isSpecial(username[i])) return Constant.ERROR_VALIDATION_USER_NAME_SPECIAL
        else if(isSpace(username[i])) return Constant.ERROR_VALIDATION_USER_NAME_SPACE
        else if(username[0].isDigit()) return Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
        else if(username.length < Constant.VALIDATION_USERNAME_LENGTH)
            return Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
    }
    return ""
}

fun isSpace(char: Char) = char == ' '

fun isSpecial(char: Char): Boolean {
    val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
    return specialChars.contains(char)
}
