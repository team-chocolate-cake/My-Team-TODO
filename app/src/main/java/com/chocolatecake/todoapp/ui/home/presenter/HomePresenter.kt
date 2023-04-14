package com.chocolatecake.todoapp.ui.home.presenter

import android.content.Context
import android.util.Log
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonalTaskResponse
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.ui.home.model.SearchQuery
import com.chocolatecake.todoapp.ui.home.model.Status
import com.chocolatecake.todoapp.ui.home.view.HomeView
import com.google.gson.Gson

class HomePresenter(private val homeView: HomeView, private val context: Context) {
    private val preferences by lazy {
        TaskSharedPreferences().also {
            it.initPreferences(context)
            it.token =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjYyNzdkNzMzLTRkN2ItNDZkZS1hMDNmLWI5MGViODEzYWQwYSIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNjE3NTQ1fQ.Wep9eEuMl0t2_oXbwbh5nGQ5_VI8eWqUkz-1z1Ol3UU"
        }
    }
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
                val teamTasksResponse = Gson().fromJson(body, TeamTasksResponse::class.java)
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
                val personalTasksResponse = Gson().fromJson(body, PersonalTaskResponse::class.java)
                val personalTasks = personalTasksResponse.tasksListPerson
                    ?.filter { it.statusPersonalTask in statusList.map { status -> status.status } }
                personalTasks?.let { homeView.onPersonalTasksSuccess(it) }
            }
        )
    }

    fun searchPersonalTasks(searchQuery: SearchQuery) {
        teamTaskService.getAllTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                if (response.code == 401) {
                    homeView.onUnauthorizedResponse()
                    return@getAllTasks
                }
                val body = response.body?.string().toString()
                val teamTasksResponse = Gson().fromJson(body, PersonalTaskResponse::class.java)
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
                val teamTasksResponse = Gson().fromJson(body, TeamTasksResponse::class.java)
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
                val teamTasksResponse = Gson().fromJson(body, TeamTasksResponse::class.java)
                val teamTasks = teamTasksResponse.value?.groupBy { it.statusTeamTask }
                val statusListCount = mutableListOf<Int>()
                if (teamTasks != null) {
                    teamTasks[Status.TODO.status]?.count()
                        ?.let { statusListCount.add(it) }
                    teamTasks[Status.PROGRESS.status]?.count()
                        ?.let { statusListCount.add(it) }
                    teamTasks[Status.DONE.status]?.count()
                        ?.let { statusListCount.add(it) }
                }
                Log.e("TAGTAG", "getTeamStatusListCount: $statusListCount")
                homeView.onStatusCountsSuccess(
                    Triple(
                        statusListCount[0],
                        statusListCount[1],
                        statusListCount[2]
                    )
                )
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
                val teamTasksResponse = Gson().fromJson(body, PersonalTaskResponse::class.java)
                val first =
                    teamTasksResponse.tasksListPerson?.count { it.statusPersonalTask == 0 }
                val second =
                    teamTasksResponse.tasksListPerson?.count { it.statusPersonalTask == 1 }
                val third =
                    teamTasksResponse.tasksListPerson?.count { it.statusPersonalTask == 2 }

                homeView.onStatusCountsSuccess(
                    Triple(
                        first,
                        second,
                        third
                    )
                )
            }
        )
    }

    private fun onFailure(message: String?) {
        homeView.onAllTasksFailure(message)
    }
}