package com.chocolatecake.todoapp.data.network.services.team

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.*

class TeamTaskService(private val preferences: TaskSharedPreferences): BaseService() {
    override val client: OkHttpClient by lazy {
        HttpClient(preferences).getClient()
    }
    private val url = Utils.getUrl("todo","team")

    fun getAllTasks(
        onFailure: (message: String?) -> Unit,
        onSuccess: (response: Response) -> Unit
    ) {
        val request = Request.Builder()
            .url(url)
            .build()
        call(request, onFailure, onSuccess)
    }

    fun createTask(
        teamTaskRequest: TeamTaskRequest,
        onFailure: (message: String?) -> Unit,
        onSuccess: (response: Response) -> Unit
    ) {
        val body = FormBody.Builder()
            .add("title", teamTaskRequest.title)
            .add("description", teamTaskRequest.description)
            .add("assignee", teamTaskRequest.assignee)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        call(request, onFailure, onSuccess)
    }

    fun updateStatus(
        id: String, status: Int,
        onFailure: (message: String?) -> Unit,
        onSuccess: (response: Response) -> Unit
    ) {
        val body = FormBody.Builder()
            .add("id", id)
            .add("status", status.toString())
            .build()
        val request = Request.Builder()
            .url(url)
            .put(body)
            .build()
        call(request, onFailure, onSuccess)
    }
}