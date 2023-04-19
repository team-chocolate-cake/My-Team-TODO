package com.chocolatecake.todoapp.features.home.view

import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask

interface HomeView {
    fun onAllTasksFailure(message: String?)
    fun onTeamTasksSuccess(teamTasks: List<TeamTask>)
    fun onPersonalTasksSuccess(personalTasks: List<PersonalTask>)
    fun onUnauthorizedResponse()

    fun onSearchTeamResultSuccess(teamTasks: List<TeamTask>)
    fun onSearchPersonalResultSuccess(personalTasks: List<PersonalTask>)
    fun onStatusCountsSuccess(statusList: Triple<Int?, Int?, Int?>)
}
