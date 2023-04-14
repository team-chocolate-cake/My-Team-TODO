package com.chocolatecake.todoapp.ui.task_details.presenter

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
     lateinit var taskDetailsView: TaskDetailsView
    private var myId: String = "7ee7e2eb-223f-4d71-bbcb-a3b75c7cb13e"

    private val preferences by lazy {
        TaskSharedPreferences().also {
            it.initPreferences(context)
            it.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjE1NzRhMDA0LWY2ZWEtNGMxNC1iMTRhLTRmY2QwZjZkNzhhMiIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNjQyMzcwfQ.zcHNx__neZb0uTmM-y4PbB76CfjF2_eB1vi9yO_aHAE"
        }
    }
    fun getTeamTaskById(id: String = myId) {
        TeamTaskService(preferences).getAllTasks(
            onSuccess = {
                val result = Gson().fromJson(it.body?.string().toString(), TeamTasksResponse::class.java)
                val taskContent = result?.value?.find { it.idTeamTask == id }
                if (taskContent != null) {
                    taskDetailsView.setDataTeam(taskContent)
                    Log.d("hi", "taskDetailsView$taskContent")
                }else {
                    Log.d("hi", "errrrrrrrrrrrrrrrrroooooooooooooorrrrrrrrrrr")
                }
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
                    Gson().fromJson(it.body?.string().toString(), PersonalTaskResponse::class.java)
                val taskContent = result.tasksListPerson!!.find { it.idPersonalTask == id }
                taskDetailsView.setDataPersonal(taskContent)
            },
            onFailure = {
                Log.i("allTasks", "fail $it")
            }
        )
    }
}