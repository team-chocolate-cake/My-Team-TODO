package com.chocolatecake.todoapp.data.network.services.team

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class TeamTaskService(
    private val preferences: TaskSharedPreferences,
    onFailure: (message: String?) -> Unit,
    onSuccess: (body: String?) -> Unit,
) : BaseService(onFailure, onSuccess) {

    override val client: OkHttpClient by lazy {
        HttpClient(preferences).getClient()
    }


    fun getAllTasks() {
        val url = HttpUrl.Builder()
            .scheme(Utils.SCHEME)
            .host(Utils.HOST)
            .addPathSegment("todo")
            .addPathSegment("team")
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        call(request)
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

    fun updateStatus(id: String, status: Int) {
        val url = HttpUrl.Builder()
            .scheme(Utils.SCHEME)
            .host(Utils.HOST)
            .addPathSegment("todo")
            .addPathSegment("team")
            .build()

        val body = FormBody.Builder()
            .add("id", id)
            .add("status", status.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .put(body)
            .build()

        call(request)
    }

    companion object {
        const val STATUS_TODO = 0
        const val STATUS_PROGRESS = 1
        const val STATUS_DONE = 2
    }

}