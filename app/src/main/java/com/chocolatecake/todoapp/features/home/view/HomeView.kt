package com.chocolatecake.todoapp.features.home.view

import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask

interface HomeView {
    fun showError(message: String?)
    fun showTeamTasks(teamTasks: List<TeamTask>)
    fun showPersonalTasks(personalTasks: List<PersonalTask>)
    fun navigateToLogin()
    fun showNoNetworkError()
    fun showNoTasksResult()
    fun updateStatusCount(statusList: Triple<Int?, Int?, Int?>)

}
