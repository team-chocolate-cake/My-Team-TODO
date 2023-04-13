package com.chocolatecake.todoapp.ui.home

interface HomeView {
    fun onAllTasksFailuer(message:String)
    fun onTeamTasksSucsess(body:String?)
    fun onPersonalTasksSucsess(body:String?)

}
