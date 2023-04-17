package com.chocolatecake.todoapp.home.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.network.services.base.BaseService.Companion.NO_NETWORK_CODE
import com.chocolatecake.todoapp.core.data.network.services.task.TaskService
import com.chocolatecake.todoapp.home.model.SearchQuery
import com.chocolatecake.todoapp.home.model.Status
import com.chocolatecake.todoapp.home.view.HomeView

class HomePresenter(private val homeView: HomeView, private val preferences: TaskSharedPreferences) {

    private val taskService by lazy { TaskService(preferences) }

    fun getTeamTask(statusList: List<Status>) {
        taskService.getAllTeamTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val teamTasks = response.value
                    ?.filter { it.statusTeamTask in statusList.map { status -> status.status } }
                teamTasks?.let { homeView.onTeamTasksSuccess(it) }
            }
        )
    }

    fun getPersonalTask(statusList: List<Status>) {
        taskService.getAllPersonalTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val personalTasks = response.value
                    ?.filter { it.statusPersonalTask in statusList.map { status -> status.status } }
                personalTasks?.let { homeView.onPersonalTasksSuccess(it) }
            }
        )
    }

    fun searchPersonalTasks(searchQuery: SearchQuery) {
        taskService.getAllPersonalTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val teamTasks = response.value
                    ?.filter {
                        it.statusPersonalTask in searchQuery.status.map { status -> status.status } &&
                                it.titlePersonalTask.contains(searchQuery.title, ignoreCase = true)
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
                        it.statusTeamTask in searchQuery.status.map { status -> status.status } &&
                                it.titleTeamTask.contains(searchQuery.title, ignoreCase = true)
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
                    response.value?.count { it.statusTeamTask == 0 }
                val second =
                    response.value?.count { it.statusTeamTask == 1 }
                val third =
                    response.value?.count { it.statusTeamTask == 2 }
                homeView.onStatusCountsSuccess(Triple(first, second, third))
            }
        )
    }

    fun getPersonalStatusListCount() {
        taskService.getAllPersonalTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val first =
                    response.value?.count { it.statusPersonalTask == 0 }
                val second =
                    response.value?.count { it.statusPersonalTask == 1 }
                val third =
                    response.value?.count { it.statusPersonalTask == 2 }
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