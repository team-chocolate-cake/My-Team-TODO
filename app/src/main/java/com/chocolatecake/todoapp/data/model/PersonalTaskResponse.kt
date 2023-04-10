package com.chocolatecake.todoapp.data.model

data class PersonalTaskResponse(
    val tasksListPerson: List<PersonTask>,
    val message: String?,
    val isSuccess: String?
)

