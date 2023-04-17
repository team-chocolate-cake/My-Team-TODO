package com.chocolatecake.todoapp.core.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class TaskSharedPreferences(private val applicationContext: Context) {

    private val preferences by lazy { applicationContext.getSharedPreferences(TOKEN, MODE_PRIVATE) }

    var token: String?
        set(value) {
            preferences.edit().putString(TOKEN, value).apply()
        }
        get() = preferences.getString(TOKEN, null)

    companion object {
        const val TOKEN = "token"
    }
}