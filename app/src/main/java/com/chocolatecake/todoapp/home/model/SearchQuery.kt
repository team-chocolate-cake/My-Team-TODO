package com.chocolatecake.todoapp.home.model

data class SearchQuery(
    val title: String = "",
    val status: List<Status> = listOf(Status.TODO, Status.PROGRESS, Status.DONE),
)
