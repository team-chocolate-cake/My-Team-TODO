package com.chocolatecake.todoapp.features.home.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.network.services.base.BaseService.Companion.NO_NETWORK_CODE
import com.chocolatecake.todoapp.core.data.network.services.task.TaskService
import com.chocolatecake.todoapp.features.home.model.SearchQuery
import com.chocolatecake.todoapp.features.home.model.Status
import com.chocolatecake.todoapp.features.home.view.HomeView

class HomePresenter(private val homeView: HomeView, private val preferences: TaskSharedPreferences) {
    private val taskService by lazy { TaskService(preferences) }

    fun getTeamTask(statusList: List<Status>) {
        taskService.getAllTeamTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val teamTasks = response.value
                    ?.filter { it.status in statusList.map { status -> status.status } }
                teamTasks?.let { homeView.onTeamTasksSuccess(it) }
            }
        )
    }

    fun searchPersonalTasks(searchQuery: SearchQuery) {
        taskService.getAllPersonalTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val teamTasks = response.value
                    ?.filter {
                        it.status in searchQuery.status.map { status -> status.status } &&
                                it.title.contains(searchQuery.title, ignoreCase = true)
                    }
                teamTasks?.let { homeView.onSearchPersonalResultSuccess(it) }
            }
        )
    }

    fun searchTeamTasks(searchQuery: SearchQuery) {
        taskService.getAllTeamTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val teamTasks = response.value
                    ?.filter {
                        it.status in searchQuery.status.map { status -> status.status } &&
                                it.title.contains(searchQuery.title, ignoreCase = true)
                    }
                teamTasks?.let { homeView.onSearchTeamResultSuccess(it) }
            }
        )
    }

    fun getTeamStatusListCount() {
        taskService.getAllTeamTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val first =
                    response.value?.count { it.status == 0 }
                val second =
                    response.value?.count { it.status == 1 }
                val third =
                    response.value?.count { it.status == 2 }
                homeView.onStatusCountsSuccess(Triple(first, second, third))
            }
        )
    }

    fun getPersonalStatusListCount() {
        taskService.getAllPersonalTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val first =
                    response.value?.count { it.status == 0 }
                val second =
                    response.value?.count { it.status == 1 }
                val third =
                    response.value?.count { it.status == 2 }
                homeView.onStatusCountsSuccess(Triple(first, second, third))
            }
        )
    }

    private fun onFailure(message: String?, statusCode: Int,) {
        when(statusCode){
            NO_NETWORK_CODE -> {
                homeView.onAllTasksFailure(message)
            }
            401 -> {
                homeView.onUnauthorizedResponse()
            }
            else -> {
                homeView.onAllTasksFailure(message)
            }
        }
    }
}