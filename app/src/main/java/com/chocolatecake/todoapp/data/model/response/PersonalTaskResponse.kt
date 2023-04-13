package com.chocolatecake.todoapp.data.model.response

import com.google.gson.annotations.SerializedName

data class PersonalTaskResponse(
    @SerializedName("value") val tasksListPerson: List<PersonTaskRequset>?,
    val message: String?,
    val isSuccess: String?
)

