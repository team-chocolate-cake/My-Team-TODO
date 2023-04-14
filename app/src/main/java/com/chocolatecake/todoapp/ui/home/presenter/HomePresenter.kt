package com.chocolatecake.todoapp.ui.home.presenter

import android.content.Context
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.ui.home.view.HomeView
import com.google.gson.Gson

class HomePresenter(private val homeView: HomeView, private val context: Context) {
    private val preferences by lazy { TaskSharedPreferences().also { it.initPreferences(context)
    it.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjYyNzdkNzMzLTRkN2ItNDZkZS1hMDNmLWI5MGViODEzYWQwYSIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNTg1MTU1fQ.c0AOIFiRHn8fijFqoaTj4Azi5jWCDeeaqZ9GsDIaffc"} }
    private val personalTaskService by lazy { PersonalTaskService(preferences) }
    private val teamTaskService by lazy { TeamTaskService(preferences) }


    fun getTeamTask(status: Set<Int>) {
        teamTaskService.getAllTasks(
            onFailure = {
                homeView.onAllTasksFailure(it)
            },
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, TeamTasksResponse::class.java)
                val teamTasks = teamTasksResponse.value?.filter { it.statusTeamTask in status }
                homeView.onTeamTasksSuccess(teamTasks)
            }
        )
    }


    fun getPersonalTask() {
        personalTaskService.getAllTasks(
            onFailure = {
                homeView.onAllTasksFailure(it)
            },
            onSuccess = {}  //::onCreateTasksSuccess
        )
    }
}