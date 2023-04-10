package com.chocolatecake.todoapp.data.model



data class TeamTasksResponse(
    val value: List<TeamTask>,
    val message: String?,
    val isSuccess: String?
)
