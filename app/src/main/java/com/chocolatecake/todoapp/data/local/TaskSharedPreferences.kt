package com.chocolatecake.todoapp.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class TaskSharedPreferences {
    private lateinit var preferences: SharedPreferences

    var token: String?
        set(value) {
            preferences.edit().putString(TOKEN, value).apply()
        }
        get() = preferences.getString(TOKEN, null)

    var expireDate: String?
        set(value) {
            preferences.edit().putString(EXPIRE_DATE, value).apply()
        }
        get() = preferences.getString(EXPIRE_DATE, null)

    fun initPreferences(context: Context) {
        preferences = context.getSharedPreferences(TOKEN, MODE_PRIVATE)
    }

    companion object{
        const val TOKEN = "token"
        const val EXPIRE_DATE = "expire_date"
    }
}