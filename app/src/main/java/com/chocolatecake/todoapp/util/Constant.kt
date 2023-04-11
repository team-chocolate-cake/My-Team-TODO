package com.chocolatecake.todoapp.util

object Constant {
    // Register Fragment Validation
    const val VALIDATION_PASSWORD_LENGTH = 8
    const val VALIDATION_USERNAME_LENGTH = 3
    const val ERROR_VALIDATION_PASSWORD_TEXT_LENGTH = "Please should be greater than 8 character"
    const val ERROR_VALIDATION_USER_NAME_SPECIAL = "Should don't have special %$@.."
    const val ERROR_VALIDATION_USER_NAME_SPACE = "Should don't have space"
    const val ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT = "Should not start with number"
    const val ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT = "Should length grater than $VALIDATION_USERNAME_LENGTH"
    const val ERROR_VALIDATION_CONFIRM_PASSWORD_MISMATCH = "The password mismatch"
}