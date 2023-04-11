package com.chocolatecake.todoapp.data.network.services.base

import okhttp3.*
import java.io.IOException

abstract class BaseService {
    abstract val client: OkHttpClient

    fun call(
        request: Request,
        onFailure: (message: String?) -> Unit,
        onSuccess: (body: String?) -> Unit
    ) {
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    onSuccess(response.body?.string().toString())
                }
            })
    }
}