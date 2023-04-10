package com.chocolatecake.todoapp.data.repository

import com.chocolatecake.todoapp.data.model.PersonTask
import com.chocolatecake.todoapp.data.model.PersonalTaskResponse

class PersonRepository (private val personId : String){

    fun getAllTasksOfPerson(list : List<PersonTask>, message : String, isSuccess : String?)
        = PersonalTaskResponse(list, message, isSuccess)

    fun addNewPersonalTask(title : String, description : String )
        = PersonTask(
                idPersonalTask = this.personId,
                titlePersonalTask = title,
                creationTime = "time here " /*LocalDateTime.now().toString(),*/ ,
                descriptionPersonalTask = description
            )

    fun changeStatus(){}


}