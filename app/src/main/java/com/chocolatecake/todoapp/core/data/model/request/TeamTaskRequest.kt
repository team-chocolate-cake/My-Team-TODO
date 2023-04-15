package com.chocolatecake.todoapp.core.data.model.request

data class TeamTaskRequest(
    val title: String,
    val description: String,
    val assignee: String,
)
