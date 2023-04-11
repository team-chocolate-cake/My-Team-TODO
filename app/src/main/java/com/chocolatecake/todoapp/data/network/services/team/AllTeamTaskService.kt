package com.chocolatecake.todoapp.data.network.services.team

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.network.services.HttpClient
import com.chocolatecake.todoapp.data.network.services.base.BaseService
import com.chocolatecake.todoapp.data.network.services.utils.Utils
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class AllTeamTaskService(
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
}