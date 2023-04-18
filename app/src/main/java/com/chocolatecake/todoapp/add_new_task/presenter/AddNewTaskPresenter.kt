package com.chocolatecake.todoapp.add_new_task.presenter

import android.content.Context
import com.chocolatecake.todoapp.add_new_task.view.AddNewTaskView
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.core.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.core.data.network.services.task.TaskService

class AddNewTaskPresenter(preferences: TaskSharedPreferences, private val addNewTaskView: AddNewTaskView) {

    private val taskService = TaskService(preferences)

    fun createPersonalTask(title: String, description: String){
        val personalTaskRequest =
            PersonalTaskRequest(
                title,
                description
            )
        taskService.createPersonalTask(
            personalTaskRequest,
            onFailure = ::onFailure,
            onSuccess = { addNewTaskView.onCreateTaskSuccess() }
        )
    }

    fun createTeamTask(title: String, description: String, assignee: String) {
        val teamTaskRequest = TeamTaskRequest(
            title,
            description,
            assignee
        )
        taskService.createTeamTask(
            teamTaskRequest,
            onFailure = ::onFailure,
            onSuccess = { addNewTaskView.onCreateTaskSuccess() }
        )
    }

    private fun onFailure(message: String?, statusCode: Int,){
        addNewTaskView.onCreateTaskFailure()
    }
}