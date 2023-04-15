package com.chocolatecake.todoapp.data.model.response.team

import com.google.gson.annotations.SerializedName

data class TeamTask(
    @SerializedName ("id") val idTeamTask: String,
    @SerializedName("title") val titleTeamTask: String,
    @SerializedName ("description") val descriptionTeamTask: String,
    val assignee: String,
    @SerializedName ("status") val statusTeamTask: Int,
    val creationTime: String
)