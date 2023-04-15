package com.chocolatecake.todoapp.task_details.presenter

import android.content.Context
import com.chocolatecake.todoapp.core.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.core.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.task_details.view.TaskDetailsView
import com.google.gson.Gson
import okhttp3.Response


class TaskDetailsPresenter(private val context: Context) {
    lateinit var taskDetailsView: TaskDetailsView
    private val sharedPreferences: com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences by lazy {
        com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences().also { it.initPreferences(context) }
    }
    private val personalTaskService: PersonalTaskService by lazy {
        PersonalTaskService(sharedPreferences)
    }
    private val teamTaskService: TeamTaskService by lazy {
        TeamTaskService(sharedPreferences)
    }

    fun updatePersonalStatus(id: String, status: Int) {
        personalTaskService.updateStatus(
            id,
            status,
            onSuccess = { response -> updateStatus(response,status) },
            onFailure = { taskDetailsView.onUpdateFailure() },
        )
    }

    fun updateTeamStatus(id: String, status: Int) {
        teamTaskService.updateStatus(
            id,
            status,
            onSuccess = { response -> updateStatus(response,status) },
            onFailure = { taskDetailsView.onUpdateFailure() },
        )
    }
    private fun updateStatus(response:Response,status: Int){
        val body = response.body?.string().toString()
        val updatedResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.UpdateResponse::class.java)
        if (updatedResponse.isSuccess) {
            taskDetailsView.onUpdateSuccess(status)
        } else {
            taskDetailsView.onUpdateFailure()
        }
    }
}