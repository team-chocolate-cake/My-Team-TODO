package com.chocolatecake.todoapp.data.model.response

data class PersonalTaskResponse(
    val tasksListPerson: List<PersonTask>?,
    val message: String?,
    val isSuccess: String?
)

