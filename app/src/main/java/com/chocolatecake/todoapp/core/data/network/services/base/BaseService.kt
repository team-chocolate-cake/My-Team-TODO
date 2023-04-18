package com.chocolatecake.todoapp.core.data.network.services.base

import com.chocolatecake.todoapp.core.data.model.response.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

abstract class BaseService {
    abstract val client: OkHttpClient

    inline fun <reified T> makeRequest(
        request: Request,
        noinline onFailure: (message: String?, statusCode: Int) -> Unit,
        noinline onSuccess: (response: BaseResponse<T>) -> Unit
    ) {
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e.message, NO_NETWORK_CODE)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body
                    if (responseBody == null) {
                        onFailure("Response body is null", response.code)
                        return
                    }

                    val responseBodyString = responseBody.string()
                    if (response.code == 401) {
                        val type: Type = object : TypeToken<BaseResponse<T>>() {}.type
                        val result =
                            Gson().fromJson<BaseResponse<T>>(responseBodyString, type)

                        val message: String = result?.message ?: response.message
                        onFailure(message, response.code)
                    } else {
                        val type: Type = object : TypeToken<BaseResponse<T>>() {}.type
                        val result =
                            Gson().fromJson<BaseResponse<T>>(responseBodyString, type)
                        onSuccess(result)
                    }
                }
            })
    }

    companion object {
        const val NO_NETWORK_CODE = -1
    }
}