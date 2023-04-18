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
                    if (response.isSuccessful) {
                        val type: Type = object : TypeToken<BaseResponse<T>>() {}.type
                        val result = Gson().fromJson<BaseResponse<T>>(response.body?.string().toString(), type)
                        onSuccess(result)
                    } else {
                        onFailure(response.message, response.code)
                    }
                }
            })
    }

    companion object{
        const val NO_NETWORK_CODE = -1
    }
}