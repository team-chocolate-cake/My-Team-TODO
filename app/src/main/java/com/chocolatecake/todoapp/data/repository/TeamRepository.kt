package com.chocolatecake.todoapp.data.repository

import com.chocolatecake.todoapp.data.model.TeamTask
import com.chocolatecake.todoapp.data.model.TeamTasksResponse
import com.chocolatecake.todoapp.data.repository.`interface`.Status

class TeamRepository(private val teamId : String  ): Status {
    
    fun getAllTeamTasks(list : List<TeamTask>, message : String, isSuccess : String?)
        = TeamTasksResponse(list, message, isSuccess)
    
    fun addNewTeamTask(title : String, assignee : String, description:String)
    = TeamTask(
            idTeamTask = this.teamId,
            titleTeamTask = title,
            assignee = assignee,
            descriptionTeamTask = description,
            creationTime = ""
    )


    override fun changeStatus() {
        TODO("Not yet implemented")
    }




}