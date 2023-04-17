package com.chocolatecake.todoapp.task_details.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.response.base.BaseResponse
import com.chocolatecake.todoapp.core.data.network.services.task.TaskService
import com.chocolatecake.todoapp.task_details.view.TaskDetailsView

class TaskDetailsPresenter(private val preferences: TaskSharedPreferences) {

    lateinit var taskDetailsView: TaskDetailsView

    private val taskService: TaskService by lazy {
        TaskService(preferences)
    }

    fun updatePersonalStatus(id: String, status: Int) {
        taskService.updatePersonalStatus(
            id,
            status,
            onSuccess = { response -> updateStatus(response, status) },
            onFailure = ::onFailure
        )
    }

    fun updateTeamStatus(id: String, status: Int) {
        taskService.updateTeamStatus(
            id,
            status,
            onSuccess = { response -> updateStatus(response, status) },
            onFailure = ::onFailure,
        )
    }

    private fun updateStatus(response: BaseResponse<String>, status: Int) {
        if (response.isSuccess) {
            taskDetailsView.onUpdateSuccess(status)
        } else {
            taskDetailsView.onUpdateFailure()
        }
    }

    private fun onFailure(message: String?, statusCode: Int) {
        taskDetailsView.onUpdateFailure()
    }
}