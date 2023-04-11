package com.chocolatecake.todoapp.util

fun String.getUsernameStatus(): String {
    for(i in this.trim().indices){
        if(isSpecial(this[i])) return Constant.ERROR_VALIDATION_USER_NAME_SPECIAL
        else if(this[i].isSpace()) return Constant.ERROR_VALIDATION_USER_NAME_SPACE
        else if(this[0].isDigit()) return Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
        else if(this.length < Constant.VALIDATION_USERNAME_LENGTH)
            return Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
    }
    return ""
}

fun Char.isSpace() = this == ' '
fun isSpecial(char: Char): Boolean {
    val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
    return specialChars.contains(char)
}
