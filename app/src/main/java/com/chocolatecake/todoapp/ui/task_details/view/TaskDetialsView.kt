package com.chocolatecake.todoapp.ui.task_details.view

import com.chocolatecake.todoapp.data.model.response.*
interface TaskDetailsView {
  fun setDataTeam(result: TeamTask?)
  fun setDataPersonal(result: PersonalTask?)
  fun onUpdateFailure()
  fun onUpdateSuccess(status:Int)
}