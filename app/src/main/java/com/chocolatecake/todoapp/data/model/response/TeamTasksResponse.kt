package com.chocolatecake.todoapp.data.model.response

import com.chocolatecake.todoapp.data.model.response.team.TeamTask

data class TeamTasksResponse(
    val value: List<TeamTask>?,
    val message: String?,
    val isSuccess: Boolean
)
