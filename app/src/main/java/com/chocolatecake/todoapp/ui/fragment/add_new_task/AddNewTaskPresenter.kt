package com.chocolatecake.todoapp.ui.fragment.add_new_task

import android.content.Context
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import okhttp3.Response

class AddNewTaskPresenter(private val context: Context,private val addNewTaskView: AddNewTaskView) {
    private var sharedPreferences = TaskSharedPreferences().also {
        it.initPreferences(context)
        it.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjE1NzRhMDA0LWY2ZWEtNGMxNC1iMTRhLTRmY2QwZjZkNzhhMiIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNDMxMDAzfQ.T0GR646YF4eQg1rMQ-kQxkF3VjPS3RwI_0jCmyIcLVU"
    }

    private val personalTaskService = PersonalTaskService(sharedPreferences)
    private val teamTaskService = TeamTaskService(sharedPreferences)

    fun createPersonalTask(title: String, description: String){
        val personalTaskRequest = PersonalTaskRequest(title, description)
        personalTaskService.createTask(
            personalTaskRequest,
            onFailure = { addNewTaskView.onCreateTaskFailure() },
            onSuccess = { addNewTaskView.onCreateTaskSuccess() }
        )
    }

    fun createTeamTask(title: String, description: String, assignee: String) {
        val teamTaskRequest = TeamTaskRequest(title, description, assignee)
        teamTaskService.createTask(
            teamTaskRequest,
            onFailure = { addNewTaskView.onCreateTaskFailure() },
            onSuccess = { addNewTaskView.onCreateTaskSuccess() }
        )
    }
}