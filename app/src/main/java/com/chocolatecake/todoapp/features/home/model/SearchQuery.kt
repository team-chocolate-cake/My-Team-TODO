package com.chocolatecake.todoapp.features.home.model

data class SearchQuery(
    val title: String = "",
    val status: List<Status> = listOf(Status.TODO),
)
