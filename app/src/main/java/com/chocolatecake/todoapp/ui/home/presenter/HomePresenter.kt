package com.chocolatecake.todoapp.ui.home.presenter

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.ui.home.HomeView

class HomePresenter(private val homeView: HomeView) {
    private val preferences by lazy { TaskSharedPreferences() }
    private val personalTaskService by lazy { PersonalTaskService(preferences)}
    private val teamTaskService by lazy { TeamTaskService(preferences)}

     fun getTeamTask() {
        teamTaskService.getAllTasks(
            onFailure ={
                       homeView.onAllTasksFailure(it)
            },
            onSuccess =  {
             //   homeView.onTeamTasksSuccess()
            }
        )
    }

     fun getPersonalTask() {
         personalTaskService.getAllTasks(
             onFailure ={
                 homeView.onAllTasksFailure(it)
             },
            onSuccess ={}  //::onCreateTasksSuccess
        )
    }
}