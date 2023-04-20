package com.chocolatecake.todoapp.features.home.presenter

import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask
import com.chocolatecake.todoapp.core.data.network.services.base.BaseService.Companion.NO_NETWORK_CODE
import com.chocolatecake.todoapp.core.data.network.services.task.TaskService
import com.chocolatecake.todoapp.features.home.model.SearchQuery
import com.chocolatecake.todoapp.features.home.model.Status
import com.chocolatecake.todoapp.features.home.view.HomeView

class HomePresenter(
    private val homeView: HomeView,
    private val preferences: TaskSharedPreferences
) {
    private val taskService by lazy { TaskService(preferences) }

    fun searchPersonalTasks(searchQuery: SearchQuery) {
        taskService.getAllPersonalTasks(
            onFailure = ::onFailure,
            onSuccess = { response ->
                val personalTasks = response.value
                    ?.filter {
                        it.status in searchQuery.status.map { status -> status.status } &&
                                it.title.contains(searchQuery.title, ignoreCase = true)
                    }
                personalTasks?.let {
                    val statusCountResult = getSelectedPersonalCount(searchQuery, response.value, it)
                    homeView.updateStatusCount(statusCountResult)
                    homeView.showPersonalTasks(it.reversed())
                    if (it.isEmpty()) {
                        homeView.showNoTasksResult()
                    }
                }
            }
        )
    }

    fun searchTeamTasks(searchQuery: SearchQuery) {
        taskService.getAllTeamTasks(
            onFailure = ::onFailure
        ) { response ->
            val teamTasks = response.value
                ?.filter {
                    it.status in searchQuery.status.map { status -> status.status } &&
                            it.title.contains(searchQuery.title, ignoreCase = true)
                }
            teamTasks?.let {
                val statusCountResult = getSelectedTeamCount(searchQuery, response.value, it)
                homeView.updateStatusCount(statusCountResult)
                homeView.showTeamTasks(it.reversed())
                if (it.isEmpty()) {
                    homeView.showNoTasksResult()
                }
            }
        }
    }

    private fun onFailure(message: String?, statusCode: Int) {
        when (statusCode) {
            NO_NETWORK_CODE -> {
                homeView.showNoNetworkError()
            }

            401 -> {
                homeView.navigateToLogin()
            }

            else -> {
                homeView.showError(message)
            }
        }
    }

    private fun getSelectedTeamCount(
        searchQuery: SearchQuery,
        originalList: List<TeamTask>,
        filteredList: List<TeamTask>
    ): Triple<Int?, Int?, Int?> {
        val statusList = searchQuery.status
        val todoCount = if (Status.TODO in statusList) {
            filteredList.count { it.status == 0 }
        } else {
            originalList.count { it.status == 0 }
        }
        val progressCount = if (Status.PROGRESS in statusList) {
            filteredList.count { it.status == 1 }
        } else {
            originalList.count { it.status == 1 }
        }
        val doneCount = if (Status.DONE in statusList) {
            filteredList.count { it.status == 2 }
        } else {
            originalList.count { it.status == 2 }
        }
        return Triple(todoCount, progressCount, doneCount)
    }
    private fun getSelectedPersonalCount(
        searchQuery: SearchQuery,
        originalList: List<PersonalTask>,
        filteredList: List<PersonalTask>
    ): Triple<Int?, Int?, Int?> {
        val statusList = searchQuery.status
        val todoCount = if (Status.TODO in statusList) {
            filteredList.count { it.status == 0 }
        } else {
            originalList.count { it.status == 0 }
        }
        val progressCount = if (Status.PROGRESS in statusList) {
            filteredList.count { it.status == 1 }
        } else {
            originalList.count { it.status == 1 }
        }
        val doneCount = if (Status.DONE in statusList) {
            filteredList.count { it.status == 2 }
        } else {
            originalList.count { it.status == 2 }
        }
        return Triple(todoCount, progressCount, doneCount)
    }
}
