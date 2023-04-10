package com.chocolatecake.todoapp.data.model.response

data class TeamTasksResponse(
    val value: List<TeamTask>?,
    val message: String?,
    val isSuccess: Boolean
)
