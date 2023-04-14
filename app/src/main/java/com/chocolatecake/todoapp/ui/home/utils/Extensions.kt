package com.chocolatecake.todoapp.ui.home.utils

import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.ui.home.model.HomeItem

fun TeamTask.toHomeItem(): HomeItem.TeamTaskItem {
    return HomeItem.TeamTaskItem(this)
}
fun PersonalTask.toHomeItem(): HomeItem.PersonalTaskItem {
    return HomeItem.PersonalTaskItem(this)
}