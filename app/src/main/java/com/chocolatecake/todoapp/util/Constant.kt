package com.chocolatecake.todoapp.util

object Constant {

    const val VALIDATION_PASSWORD_LENGTH = 8
    const val VALIDATION_USERNAME_LENGTH = 3
    const val ERROR_VALIDATION_PASSWORD_TEXT_LENGTH = "Please should be greater than 8 character"
    const val ERROR_VALIDATION_USER_NAME_SPECIAL = "should don't have special %$@.."
    const val ERROR_VALIDATION_USER_NAME_SPACE = "should don't have space"
    const val ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT = "should not start with number"
    const val ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT = "should length grater than $VALIDATION_USERNAME_LENGTH"
}