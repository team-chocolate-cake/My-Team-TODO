package com.chocolatecake.todoapp.data.network.services.personal

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonTaskRequset
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.*

class PersonalTaskService(
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
            .addPathSegment("personal")
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        call(request)
    }

    fun createTask(personTaskRequest: PersonTaskRequset) {
        val url = HttpUrl.Builder()
            .scheme(Utils.SCHEME)
            .host(Utils.HOST)
            .addPathSegment("todo")
            .addPathSegment("personal")
            .build()

        val requestBody = FormBody.Builder()
            .add("title", personTaskRequest.titlePersonalTask)
            .add("description", personTaskRequest.descriptionPersonalTask)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        call(request)
    }

    fun updateStatus(id: String, status: Int) {
        val url = HttpUrl.Builder()
            .scheme(Utils.SCHEME)
            .host(Utils.HOST)
            .addPathSegment("todo")
            .addPathSegment("personal")
            .build()

        val requestBody = FormBody.Builder()
            .add("id", id)
            .add("status", status.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()

        call(request)
    }

    companion object {
        const val STATUS_TODO = 0
        const val STATUS_PROGRESS = 1
        const val STATUS_DONE = 2
    }
}
