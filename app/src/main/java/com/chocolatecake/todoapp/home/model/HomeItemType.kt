package com.chocolatecake.todoapp.home.model

sealed class HomeItem(val type: HomeItemType) {

    data class TeamTaskItem(val teamTask: com.chocolatecake.todoapp.core.data.model.response.TeamTask) : HomeItem(
        HomeItemType.TYPE_TEAM_TASK
    )

    data class PersonalTaskItem(val personalTask: com.chocolatecake.todoapp.core.data.model.response.PersonalTask) :
        HomeItem(HomeItemType.TYPE_PERSONAL_TASK)
}

enum class HomeItemType {
  TYPE_TEAM_TASK, TYPE_PERSONAL_TASK
}