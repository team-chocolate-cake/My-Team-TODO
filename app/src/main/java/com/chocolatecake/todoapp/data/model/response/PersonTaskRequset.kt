package com.chocolatecake.todoapp.data.model.response

import com.google.gson.annotations.SerializedName

data class PersonTaskRequset(
    @SerializedName ("id") val idPersonalTask: String,
    @SerializedName ("title") val titlePersonalTask: String,
    @SerializedName ("description") val descriptionPersonalTask: String,
    @SerializedName ("status") val statusPersonTask: Int,
    val creationTime: String
)