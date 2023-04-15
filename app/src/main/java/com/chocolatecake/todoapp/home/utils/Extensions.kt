package com.chocolatecake.todoapp.home.utils

import com.chocolatecake.todoapp.home.model.HomeItem

fun com.chocolatecake.todoapp.core.data.model.response.TeamTask.toHomeItem(): HomeItem.TeamTaskItem {
    return HomeItem.TeamTaskItem(this)
}
fun com.chocolatecake.todoapp.core.data.model.response.PersonalTask.toHomeItem(): HomeItem.PersonalTaskItem {
    return HomeItem.PersonalTaskItem(this)
}