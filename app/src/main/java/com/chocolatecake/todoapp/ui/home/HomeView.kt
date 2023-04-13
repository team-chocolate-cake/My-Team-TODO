package com.chocolatecake.todoapp.ui.home

import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask

interface HomeView {
    fun onAllTasksFailure(message: String?)
    fun onTeamTasksSuccess(teamTasks: List<TeamTask>?)
    fun onPersonalTasksSuccess(personalTasks: List<PersonalTask>?)

}
