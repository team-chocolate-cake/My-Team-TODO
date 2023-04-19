package com.chocolatecake.todoapp.features.task_details.view

import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask

interface TaskDetailsView {
  fun showTeamTaskData(result: TeamTask?)
  fun showPersonalTaskData(result: PersonalTask?)
  fun showFailedStatusUpdate()
  fun showUpdatedStatus(status:Int)
}