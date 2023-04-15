package com.chocolatecake.todoapp.core.data.model.response

data class TeamTasksResponse(
    val value: List<com.chocolatecake.todoapp.core.data.model.response.TeamTask>?,
    val message: String?,
    val isSuccess: Boolean
)
