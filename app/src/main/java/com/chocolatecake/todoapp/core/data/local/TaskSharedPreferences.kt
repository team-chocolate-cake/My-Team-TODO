package com.chocolatecake.todoapp.core.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class TaskSharedPreferences {
    private lateinit var preferences: SharedPreferences

    var token: String?
        set(value) {
            preferences.edit().putString(com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences.Companion.TOKEN, value).apply()
        }
        get() = preferences.getString(com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences.Companion.TOKEN, null)

    var expireDate: String?
        set(value) {
            preferences.edit().putString(com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences.Companion.EXPIRE_DATE, value).apply()
        }
        get() = preferences.getString(com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences.Companion.EXPIRE_DATE, null)

    fun initPreferences(context: Context) {
        preferences = context.getSharedPreferences(com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences.Companion.TOKEN, MODE_PRIVATE)
    }

    companion object{
        const val TOKEN = "token"
        const val EXPIRE_DATE = "expire_date"
    }
}