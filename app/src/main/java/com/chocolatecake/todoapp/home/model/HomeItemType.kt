package com.chocolatecake.todoapp.home.model

import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask

sealed class HomeItem(val type: HomeItemType) {

    data class TeamTaskItem(val teamTask: TeamTask) : HomeItem(
        HomeItemType.TYPE_TEAM_TASK
    )

    data class PersonalTaskItem(val personalTask: PersonalTask) :
        HomeItem(HomeItemType.TYPE_PERSONAL_TASK)
}

enum class HomeItemType {
  TYPE_TEAM_TASK, TYPE_PERSONAL_TASK
}