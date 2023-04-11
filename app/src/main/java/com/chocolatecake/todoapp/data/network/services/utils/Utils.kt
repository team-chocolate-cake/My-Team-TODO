package com.chocolatecake.todoapp.data.network.services.utils

import okhttp3.HttpUrl

object Utils {
    fun getUrl(path: String, subPath: String = ""): HttpUrl {
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .addPathSegment(path)
            .apply {
                if (subPath.isNotEmpty()) {
                    addPathSegment(subPath)
                }
            }
            .build()
    }

    private const val SCHEME = "https"
    private const val HOST = "team-todo-62dmq.ondigitalocean.app"
}