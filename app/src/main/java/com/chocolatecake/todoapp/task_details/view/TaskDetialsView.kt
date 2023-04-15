package com.chocolatecake.todoapp.task_details.view

interface TaskDetailsView {
  fun setDataTeam(result: com.chocolatecake.todoapp.core.data.model.response.TeamTask?)
  fun setDataPersonal(result: com.chocolatecake.todoapp.core.data.model.response.PersonalTask?)
  fun onUpdateFailure()
  fun onUpdateSuccess(status:Int)
}