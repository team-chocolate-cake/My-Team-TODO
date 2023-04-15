package com.chocolatecake.todoapp.ui.home.model

import com.chocolatecake.todoapp.data.model.response.personal.PersonalTask
import com.chocolatecake.todoapp.data.model.response.team.TeamTask

sealed class HomeItem(val type: HomeItemType) {

    data class TeamTaskItem(val teamTask: TeamTask) : HomeItem(HomeItemType.TYPE_TEAM_TASK)

    data class PersonalTaskItem(val personalTask: PersonalTask) :
        HomeItem(HomeItemType.TYPE_PERSONAL_TASK)
}

enum class HomeItemType {
  TYPE_TEAM_TASK, TYPE_PERSONAL_TASK
}