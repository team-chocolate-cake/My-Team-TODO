package com.chocolatecake.todoapp.ui.home.presenter

import android.content.Context
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonalTaskResponse
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.ui.home.view.HomeView
import com.google.gson.Gson

class HomePresenter(private val homeView: HomeView, private val context: Context) {
    private val preferences by lazy { TaskSharedPreferences().also { it.initPreferences(context) } }
    private val personalTaskService by lazy { PersonalTaskService(preferences) }
    private val teamTaskService by lazy { TeamTaskService(preferences) }

    fun getTeamTask(status: Set<Int>) {
        teamTaskService.getAllTasks(
            onFailure = { homeView.onAllTasksFailure(it) },
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, TeamTasksResponse::class.java)
                val teamTasks = teamTasksResponse.value?.filter { it.statusTeamTask in status }
                teamTasks?.let { homeView.onTeamTasksSuccess(it) }
            }
        )
    }

    fun getPersonalTask(status: Set<Int>) {
        personalTaskService.getAllTasks(
            onFailure = { homeView.onAllTasksFailure(it) },
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, PersonalTaskResponse::class.java)
                val teamTasks = teamTasksResponse.tasksListPerson?.filter { it.statusPersonTask in status }
                teamTasks?.let { homeView.onPersonalTasksSuccess(it) }
            }
        )
    }
}