package com.chocolatecake.todoapp.data.network.services.personal

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonTaskRequset
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.*

class PersonalTaskService(
    private val preferences: TaskSharedPreferences
) : BaseService() {
    override val client: OkHttpClient by lazy {
        HttpClient(preferences).getClient()
    }

    fun getAllTasks(
        onFailure: (message: String?) -> Unit,
        onSuccess: (body: String?) -> Unit
    ) {
        val url = getUrl("personal")
        val request = Request.Builder()
            .url(url)
            .build()

        call(request, onFailure, onSuccess)
    }

    fun createTask(
        personTaskRequest: PersonTaskRequset,
        onFailure: (message: String?) -> Unit,
        onSuccess: (body: String?) -> Unit
    ) {
        val url = getUrl("personal")

        val requestBody = FormBody.Builder()
            .add("title", personTaskRequest.titlePersonalTask)
            .add("description", personTaskRequest.descriptionPersonalTask)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        call(request, onFailure, onSuccess)
    }

    fun updateStatus(
        id: String, status: Int,
        onFailure: (message: String?) -> Unit,
        onSuccess: (body: String?) -> Unit
    ) {
        val url = getUrl("personal")

        val requestBody = FormBody.Builder()
            .add("id", id)
            .add("status", status.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()

        call(request, onFailure, onSuccess)
    }

    private fun getUrl(path: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme(Utils.SCHEME)
            .host(Utils.HOST)
            .addPathSegment("todo")
            .addPathSegment(path)
            .build()
    }

    companion object {
        const val STATUS_TODO = 0
        const val STATUS_PROGRESS = 1
        const val STATUS_DONE = 2
    }
}
