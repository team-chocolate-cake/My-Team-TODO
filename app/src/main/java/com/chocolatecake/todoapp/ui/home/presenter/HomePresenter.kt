package com.chocolatecake.todoapp.ui.home.presenter

import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService

class HomePresenter {
    private val preferences by lazy{
        TaskSharedPreferences()
    }
    private val personalTaskService by lazy { PersonalTaskService(preferences)}
    private val TeamTaskService by lazy { TeamTaskService(preferences)}
}