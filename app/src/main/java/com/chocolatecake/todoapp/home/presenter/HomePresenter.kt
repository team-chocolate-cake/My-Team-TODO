package com.chocolatecake.todoapp.home.presenter

import android.content.Context
import com.chocolatecake.todoapp.core.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.core.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.home.model.SearchQuery
import com.chocolatecake.todoapp.home.model.Status
import com.chocolatecake.todoapp.home.view.HomeView
import com.google.gson.Gson

class HomePresenter(private val homeView: HomeView, private val context: Context) {
    private val preferences by lazy { com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences()
        .also { it.initPreferences(context) } }
    private val personalTaskService by lazy { PersonalTaskService(preferences) }
    private val teamTaskService by lazy { TeamTaskService(preferences) }

    fun getTeamTask(statusList: List<Status>) {
        teamTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.TeamTasksResponse::class.java)
                val teamTasks = teamTasksResponse.value
                    ?.filter { it.statusTeamTask in statusList.map { status -> status.status } }
                teamTasks?.let { homeView.onTeamTasksSuccess(it) }
            }
        )
    }

    fun getPersonalTask(statusList: List<Status>) {
        personalTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val personalTasksResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.PersonalTaskResponse::class.java)
                val personalTasks = personalTasksResponse.tasksListPerson
                    ?.filter { it.statusPersonalTask in statusList.map { status -> status.status } }
                personalTasks?.let { homeView.onPersonalTasksSuccess(it) }
            }
        )
    }

    fun searchPersonalTasks(searchQuery: SearchQuery) {
        personalTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.PersonalTaskResponse::class.java)
                val teamTasks = teamTasksResponse.tasksListPerson
                    ?.filter {
                        it.statusPersonalTask in searchQuery.status.map { status -> status.status } &&
                                it.titlePersonalTask.contains(searchQuery.title, ignoreCase = true)
                    }
                teamTasks?.let { homeView.onSearchPersonalResultSuccess(it) }
            }
        )
    }

    fun searchTeamTasks(searchQuery: SearchQuery) {
        teamTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.TeamTasksResponse::class.java)
                val teamTasks = teamTasksResponse.value
                    ?.filter {
                        it.statusTeamTask in searchQuery.status.map { status -> status.status } &&
                                it.titleTeamTask.contains(searchQuery.title, ignoreCase = true)
                    }
                teamTasks?.let { homeView.onSearchTeamResultSuccess(it) }
            }
        )
    }

    fun getTeamStatusListCount() {
        teamTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.TeamTasksResponse::class.java)
                val first =
                    teamTasksResponse.value?.count { it.statusTeamTask == 0 }
                val second =
                    teamTasksResponse.value?.count { it.statusTeamTask == 1 }
                val third =
                    teamTasksResponse.value?.count { it.statusTeamTask == 2 }
                homeView.onStatusCountsSuccess(Triple(first, second, third))
            }
        )
    }

    fun getPersonalStatusListCount() {
        personalTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, com.chocolatecake.todoapp.core.data.model.response.PersonalTaskResponse::class.java)
                val first =
                    teamTasksResponse.tasksListPerson?.count { it.statusPersonalTask == 0 }
                val second =
                    teamTasksResponse.tasksListPerson?.count { it.statusPersonalTask == 1 }
                val third =
                    teamTasksResponse.tasksListPerson?.count { it.statusPersonalTask == 2 }
                homeView.onStatusCountsSuccess(Triple(first, second, third))
            }
        )
    }

    private fun onFailure(message: String?) {
        homeView.onAllTasksFailure(message)
    }
}