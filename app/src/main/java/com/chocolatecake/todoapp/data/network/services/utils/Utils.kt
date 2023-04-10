package com.chocolatecake.todoapp.data.network.services.utils

import okhttp3.HttpUrl

object Utils {
    fun getUrl(path: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .addPathSegment(path)
            .build()
    }
    const val SCHEME ="https"
    const val HOST ="team-todo-62dmq.ondigitalocean.app"
}