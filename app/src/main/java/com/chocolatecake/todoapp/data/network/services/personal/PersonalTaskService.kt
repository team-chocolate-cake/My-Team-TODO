package com.chocolatecake.todoapp.data.network.services.personal

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class PersonalTaskService(private val preferences: TaskSharedPreferences): BaseService() {
    override val client: OkHttpClient by lazy {
        HttpClient(preferences).getClient()
    }
    private val url = Utils.getUrl("todo","personal")

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
        personTaskRequest: PersonalTaskRequest,
        onFailure: (message: String?) -> Unit,
        onSuccess: (response: Response) -> Unit
    ) {
        val requestBody = FormBody.Builder()
            .add("title", personTaskRequest.title)
            .add("description", personTaskRequest.description)
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
        onSuccess: (response: Response) -> Unit
    ) {
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
}
