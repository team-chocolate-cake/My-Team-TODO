package com.chocolatecake.todoapp.core.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalTask(
    @SerializedName ("id") val idPersonalTask: String,
    @SerializedName ("title") val titlePersonalTask: String,
    @SerializedName ("description") val descriptionPersonalTask: String,
    @SerializedName ("status") val statusPersonalTask: Int,
    val creationTime: String
) : Parcelable