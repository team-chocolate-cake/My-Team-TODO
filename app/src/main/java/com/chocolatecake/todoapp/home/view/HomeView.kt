package com.chocolatecake.todoapp.home.view

interface HomeView {
    fun onAllTasksFailure(message: String?)
    fun onTeamTasksSuccess(teamTasks: List<com.chocolatecake.todoapp.core.data.model.response.TeamTask>)
    fun onPersonalTasksSuccess(personalTasks: List<com.chocolatecake.todoapp.core.data.model.response.PersonalTask>)
    fun onUnauthorizedResponse()

    fun onSearchTeamResultSuccess(teamTasks: List<com.chocolatecake.todoapp.core.data.model.response.TeamTask>)
    fun onSearchPersonalResultSuccess(personalTasks: List<com.chocolatecake.todoapp.core.data.model.response.PersonalTask>)
    fun onStatusCountsSuccess(statusList: Triple<Int?, Int?, Int?>)
}
