package com.chocolatecake.todoapp.core.data.network.services.task

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.core.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask
import com.chocolatecake.todoapp.core.data.model.response.base.BaseResponse
import com.chocolatecake.todoapp.core.data.network.services.TaskClient
import com.chocolatecake.todoapp.core.data.network.services.base.BaseService
import com.chocolatecake.todoapp.core.data.network.services.utils.Constants.PERSONAL_ENDPOINT
import com.chocolatecake.todoapp.core.data.network.services.utils.Constants.TEAM_ENDPOINT
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class TaskService(private val preferences: TaskSharedPreferences) : BaseService() {
    override val client: OkHttpClient by lazy {
        TaskClient(preferences).getClient()
    }

    fun getAllTeamTasks(
        onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<List<TeamTask>>) -> Unit
    ) {
        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }

    fun createTeamTask(
        teamTaskRequest: TeamTaskRequest,
        onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<TeamTask>) -> Unit
    ) {
        val body = FormBody.Builder()
            .add("title", teamTaskRequest.title)
            .add("description", teamTaskRequest.description)
            .add("assignee", teamTaskRequest.assignee)
            .build()
        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .post(body)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }

    fun updateTeamStatus(
        id: String, status: Int,
        onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<String>) -> Unit
    ) {
        val body = FormBody.Builder()
            .add("id", id)
            .add("status", status.toString())
            .build()
        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .put(body)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }


    fun getAllPersonalTasks(
        onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<List<PersonalTask>>) -> Unit
    ) {
        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }

    fun createPersonalTask(
        personTaskRequest: PersonalTaskRequest,
        onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<PersonalTask>) -> Unit
    ) {
        val requestBody = FormBody.Builder()
            .add("title", personTaskRequest.title)
            .add("description", personTaskRequest.description)
            .build()
        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .post(requestBody)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }

    fun updatePersonalStatus(
        id: String, status: Int,
        onFailure: (message: String?, statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<String>) -> Unit
    ) {
        val requestBody = FormBody.Builder()
            .add("id", id)
            .add("status", status.toString())
            .build()
        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .put(requestBody)
            .build()
        makeRequest(request, onFailure, onSuccess)
    }
}