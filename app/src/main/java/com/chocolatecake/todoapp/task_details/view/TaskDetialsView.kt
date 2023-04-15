package com.chocolatecake.todoapp.task_details.view

import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask

interface TaskDetailsView {
  fun setDataTeam(result: TeamTask?)
  fun setDataPersonal(result: PersonalTask?)
  fun onUpdateFailure()
  fun onUpdateSuccess(status:Int)
}