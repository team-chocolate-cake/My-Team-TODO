package com.chocolatecake.todoapp.ui.home.view

import com.chocolatecake.todoapp.data.model.response.*

interface TaskDetailsView {
  fun setDataTeam(result: TeamTask)
  fun setDataPersonal(result: PersonTaskRequset)
  fun onCreateTaskFailure()
  fun onCreateTaskSuccess()
}