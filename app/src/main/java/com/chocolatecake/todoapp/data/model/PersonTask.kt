package com.chocolatecake.todoapp.data.model

import com.google.gson.annotations.SerializedName

data class PersonTask(
    @SerializedName ("id") val idPersonalTask: String,
    @SerializedName ("title") val titlePersonalTask: String,
    @SerializedName ("description") val descriptionPersonalTask: String,
//    @SerializedName ("status") val statusPersonTask: Int,
    val creationTime: String
)