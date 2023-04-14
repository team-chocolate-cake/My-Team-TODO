package com.chocolatecake.todoapp.ui.home.presenter

import android.content.Context
import android.util.Log
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonalTaskResponse
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.ui.home.view.TaskDetailsView
import com.google.gson.Gson

class TaskDetailsPresenter(private val context: Context) {
    private lateinit var taskDetailsView: TaskDetailsView
    private var test:String="44210916-1b5c-4849-87fd-8761fca60827"

    private val preferences by lazy {
        TaskSharedPreferences().also {
            it.initPreferences(context)
            it.token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"
        }
    }


   fun getTeamTaskById(id:String=test) {
         TeamTaskService(preferences).getAllTasks(
            onSuccess = {
                val result =
                    Gson().fromJson(it.body?.string().toString(), TeamTasksResponse::class.java)
                val taskContent = result.value?.find { it.idTeamTask == id }
                taskDetailsView.setDataTeam(taskContent?)

                Log.d("hi","taskDetailsView$taskContent")
            },
            onFailure = {
                Log.i("hi", "fail $it")

            }
        )
    }
   fun getPersonalTaskById(id: String) {
         PersonalTaskService(preferences).getAllTasks(
            onSuccess = {
                val result =
                    Gson().fromJson(it.body?.string().toString(),PersonalTaskResponse::class.java)
                val taskContent = result.tasksListPerson?.find { it.idPersonalTask == id }
                taskDetailsView.setDataPersonal(taskContent!!)
            },
            onFailure = {
                Log.i("allTasks", "fail $it")
            }
        )
    }
}