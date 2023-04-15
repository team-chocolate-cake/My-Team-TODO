package com.chocolatecake.todoapp.ui.home.utils

import com.chocolatecake.todoapp.data.model.response.personal.PersonalTask
import com.chocolatecake.todoapp.data.model.response.team.TeamTask
import com.chocolatecake.todoapp.ui.home.model.HomeItem

fun TeamTask.toHomeItem(): HomeItem.TeamTaskItem {
    return HomeItem.TeamTaskItem(this)
}
fun PersonalTask.toHomeItem(): HomeItem.PersonalTaskItem {
    return HomeItem.PersonalTaskItem(this)
}