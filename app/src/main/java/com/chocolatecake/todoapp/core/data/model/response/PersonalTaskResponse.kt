package com.chocolatecake.todoapp.core.data.model.response

import com.google.gson.annotations.SerializedName

data class PersonalTaskResponse(
    @SerializedName("value") val tasksListPerson: List<PersonalTask>?,
    val message: String?,
    val isSuccess: String?
)

