package com.chocolatecake.todoapp.ui.home.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonTaskRequset
import com.chocolatecake.todoapp.data.model.response.PersonalTaskResponse
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentTaskDetailsBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.presenter.TaskDetailsPresenter
import com.google.gson.Gson

class TaskDetailsFragment() : BaseFragment<FragmentTaskDetailsBinding>(),TaskDetailsView {
  private val taskDetailsPresenter:TaskDetailsPresenter by lazy {
      TaskDetailsPresenter(requireContext())
  }
    private val isPersonalType by lazy { arguments?.getBoolean(IS_PERSONAL, false) }
    private val idTask by lazy { arguments?.getString(TASK_ID) }
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTaskDetailsBinding =
        FragmentTaskDetailsBinding::inflate
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //taskDetailsPresenter.getTeamTaskById("44210916-1b5c-4849-87fd-8761fca60827")
        getTask()
    }
    private fun getTask() {
        if (isPersonalType == true) {
            idTask?.let { taskDetailsPresenter.getPersonalTaskById(it) }
        } else {
            idTask?.let { taskDetailsPresenter.getTeamTaskById(it) }
            Log.d("hi","${taskDetailsPresenter.getTeamTaskById("44210916-1b5c-4849-87fd-8761fca60827")}")
        }
    }

    private fun getStatusByNum(num: Int): String {
        return when (num) {
            0 -> getString(R.string.status_todo)
            1 -> getString(R.string.status_inprogress)
            2 -> getString(R.string.status_done)
            else -> "unknown"
        }
    }companion object {
        private const val TASK_ID = "44210916-1b5c-4849-87fd-8761fca60827"
        private const val IS_PERSONAL = "Is_Personal"
        fun newInstance(taskId: String, isPersonal: Boolean) =
            TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL, isPersonal)
                    putString(TASK_ID, taskId)
                }
            }
    }

    override fun setDataTeam(result: TeamTask) {
        with(binding) {
            textViewTitle.text = result.titleTeamTask
            textViewAssignee.text = result.assignee
            textViewDescription.text = result.descriptionTeamTask
            textViewStatus.text = getStatusByNum(result.statusTeamTask).toString()
        }
    }

    override fun setDataPersonal(result: PersonTaskRequset) {
        with(binding) {
            textViewTitle.text = result.titlePersonalTask
            textViewDescription.text = result.descriptionPersonalTask
            textViewStatus.text = getStatusByNum(result.statusPersonTask).toString()
        }
    }
    override fun onCreateTaskFailure() {
        TODO("Not yet implemented")
    }

    override fun onCreateTaskSuccess() {
        TODO("Not yet implemented")
    }
}