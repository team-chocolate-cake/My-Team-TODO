package com.chocolatecake.todoapp.core.data.network.services.base

import com.chocolatecake.todoapp.core.data.model.response.base.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

abstract class BaseService {
    abstract val client: OkHttpClient

    fun <T> makeRequest(
        request: Request,
        onFailure: (message: String? , statusCode: Int,) -> Unit,
        onSuccess: (response: BaseResponse<T>) -> Unit
    ) {
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e.message , NO_NETWORK_CODE)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val type = object : TypeToken<BaseResponse<T>>() {}.type
                        val body = response.body?.string().toString()
                        val result = Gson().fromJson<BaseResponse<T>>(body, type)
                        onSuccess(result)
                    } else {
                        onFailure(response.message , response.code)
                    }
                }
            })
    }

    companion object{
        const val NO_NETWORK_CODE = -1
    }
}