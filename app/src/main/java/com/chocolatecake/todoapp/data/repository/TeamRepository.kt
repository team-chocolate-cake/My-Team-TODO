package com.chocolatecake.todoapp.data.repository

import com.chocolatecake.todoapp.data.model.TeamTask
import com.chocolatecake.todoapp.data.model.TeamTasksList

class TeamRepository{
    
    fun getAllTeamTasks(list : List<TeamTask>) : TeamTasksList = TeamTasksList(list)
    
    fun addNewTeamTask(title : String, assignee : String, description:String){
        
        
    }
}