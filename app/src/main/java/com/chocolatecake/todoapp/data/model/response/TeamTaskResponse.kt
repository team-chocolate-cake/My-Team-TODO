package com.chocolatecake.todoapp.data.model.response

import com.chocolatecake.todoapp.data.model.response.team.TeamTask

data class TeamTaskResponse(
    val value: TeamTask?,
    val message: String?,
    val isSuccess: Boolean
)
