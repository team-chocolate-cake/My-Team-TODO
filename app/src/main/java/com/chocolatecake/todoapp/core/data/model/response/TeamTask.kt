package com.chocolatecake.todoapp.core.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamTask(
    @SerializedName("id") val idTeamTask: String,
    @SerializedName("title") val titleTeamTask: String,
    @SerializedName("description") val descriptionTeamTask: String,
    @SerializedName("assignee") val assignee: String,
    @SerializedName("status") val statusTeamTask: Int,
    @SerializedName("creationTime") val creationTime: String,
) : Parcelable