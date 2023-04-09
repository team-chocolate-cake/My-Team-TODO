package com.chocolatecake.todoapp.data.repository

import com.chocolatecake.todoapp.data.model.PersonTask
import com.chocolatecake.todoapp.data.model.PersonalTaskList

class PersonRepository (private val personId : String){

    fun getAllTasksOfPerson(list : List<PersonTask>) : PersonalTaskList
        = PersonalTaskList(list)
    /**
    * @param title the title who you take it from title input field
    * @param description the description who you take it from description
    * @return PersonTask(...)
    */
    fun addNewPersonalTask(title : String, description : String ): PersonTask
        = PersonTask(
                idPersonalTask = this.personId,
                titlePersonalTask = title,
                creationTime = "time here " /*LocalDateTime.now().toString(),*/ ,
                descriptionPersonalTask = description
            )

    fun changeStatus(){}

}