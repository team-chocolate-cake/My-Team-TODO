package com.chocolatecake.todoapp.data.network.services.team

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.*

class CreationTeamTaskService(
    private val preferences: TaskSharedPreferences,
    onFailure: (message: String?) -> Unit,
    onSuccess: (body: String?) -> Unit,
) : BaseService(onFailure, onSuccess) {
    override val client: OkHttpClient by lazy {
        HttpClient(preferences).getClient()
    }

    fun createTask(teamTaskRequest: TeamTaskRequest) {
        val url = HttpUrl.Builder()
            .scheme(Utils.SCHEME)
            .host(Utils.HOST)
            .addPathSegment("todo")
            .addPathSegment("team")
            .build()

        val body = FormBody.Builder()
            .add("title", teamTaskRequest.title)
            .add("description", teamTaskRequest.description)
            .add("assignee", teamTaskRequest.assignee)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        call(request)
    }
}