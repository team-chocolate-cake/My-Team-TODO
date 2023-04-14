package com.chocolatecake.todoapp.ui.home.utils

import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.ui.home.model.HomeItemType

fun TeamTask.toHomeItem(): HomeItem<Any> {
    return HomeItem(this, HomeItemType.TYPE_TASKS)
}
fun PersonalTask.toHomeItem(): HomeItem<Any> {
    return HomeItem(this, HomeItemType.TYPE_TASKS)
}