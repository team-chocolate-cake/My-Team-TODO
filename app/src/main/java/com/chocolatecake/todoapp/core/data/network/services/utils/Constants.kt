package com.chocolatecake.todoapp.core.data.network.services.utils

object Constants {
    private const val BASE_URL = "https://team-todo-62dmq.ondigitalocean.app"
    const val LOGIN_ENDPOINT = "${BASE_URL}/login"
    const val SIGNUP_ENDPOINT = "${BASE_URL}/signup"
    const val PERSONAL_ENDPOINT = "${BASE_URL}/todo/personal"
    const val TEAM_ENDPOINT = "${BASE_URL}/todo/team"
}