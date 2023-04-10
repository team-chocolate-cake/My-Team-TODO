package com.chocolatecake.todoapp.data.model.request

data class TeamTaskRequest(
    val title: String,
    val description: String,
    val assignee: String,
)
