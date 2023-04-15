package com.chocolatecake.todoapp.data.model.response

import com.chocolatecake.todoapp.data.model.response.personal.PersonalTask
import com.google.gson.annotations.SerializedName

data class PersonalTaskResponse(
    @SerializedName("value") val tasksListPerson: List<PersonalTask>?,
    val message: String?,
    val isSuccess: String?
)

