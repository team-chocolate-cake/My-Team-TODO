package com.chocolatecake.todoapp.data.model.response

data class PersonalTaskResponse(
    val tasksListPerson: List<PersonalTask>?,
    val message: String?,
    val isSuccess: String?
)

