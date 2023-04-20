package com.chocolatecake.todoapp.features.add_new_task.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.core.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.core.data.network.services.base.BaseService
import com.chocolatecake.todoapp.core.data.network.services.task.TaskService
import com.chocolatecake.todoapp.features.add_new_task.view.AddNewTaskView

class AddNewTaskPresenter(
    preferences: TaskSharedPreferences,
    private val addNewTaskView: AddNewTaskView
) {

    private val taskService = TaskService(preferences)

    fun createPersonalTask(title: String, description: String) {
        val personalTaskRequest = PersonalTaskRequest(title, description)
        taskService.createPersonalTask(
            personalTaskRequest,
            onFailure = ::onFailure,
            onSuccess = { addNewTaskView.showAddTaskSuccess() }
        )
    }

    fun createTeamTask(title: String, description: String, assignee: String) {
        val teamTaskRequest = TeamTaskRequest(title, description, assignee)
        taskService.createTeamTask(teamTaskRequest,
            onFailure = ::onFailure,
            onSuccess = { addNewTaskView.showAddTaskSuccess() }
        )
    }

    private fun onFailure(message: String?, statusCode: Int) {
        when (statusCode) {
            BaseService.NO_NETWORK_CODE -> {
                addNewTaskView.showNoNetworkError()

            } else -> {
                addNewTaskView.showError(message.toString())
            }
        }
    }
}