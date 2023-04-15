package com.chocolatecake.todoapp.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamTask(
    @SerializedName ("id") val idTeamTask: String,
    @SerializedName("title") val titleTeamTask: String,
    @SerializedName ("description") val descriptionTeamTask: String,
    val assignee: String,
    @SerializedName ("status") val statusTeamTask: Int,
    val creationTime: String
):Parcelable