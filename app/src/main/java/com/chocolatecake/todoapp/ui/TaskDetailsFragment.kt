package com.chocolatecake.todoapp.ui.fragment.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.PersonalTaskResponse
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentTaskDetailsBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.google.gson.Gson

class TaskDetailsFragment() : BaseFragment<FragmentTaskDetailsBinding>() {
    private val isPersonalType by lazy { arguments?.getBoolean(IS_PERSONAL, true) }
    private val idTask by lazy { arguments?.getString(TASK_ID) }
    private val preferences by lazy { TaskSharedPreferences() }
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTaskDetailsBinding =
        FragmentTaskDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTask()


    }

    private fun getTeamTaskById(id: String) {
        val tasks = TeamTaskService(preferences).getAllTasks(
            onSuccess = {
                requireActivity().runOnUiThread() {
                    val result =
                        Gson().fromJson(it.body?.string().toString(), TeamTasksResponse::class.java)
                    with(binding) {
                        val taskContent = result.value?.find { it.idTeamTask == id }
                        textViewTitle.text = taskContent?.titleTeamTask
                        textViewAssignee.text = taskContent?.assignee
                        textViewDescription.text = taskContent?.descriptionTeamTask
                        textViewStatus.text =
                            getStatusByNum(taskContent!!.statusTeamTask).toString()
                    }
                }
            },
            onFailure = {
                Log.i("allTasks", "fail $it")

            }

        )
    }

    private fun getPersonalTaskById(id: String) {
        val tasks = PersonalTaskService(preferences).getAllTasks(
            onSuccess = {
                requireActivity().runOnUiThread() {
                    val result =
                        Gson().fromJson(
                            it.body?.string().toString(),
                            PersonalTaskResponse::class.java
                        )
                    with(binding) {
                        val taskContent = result.tasksListPerson?.find { it.idPersonalTask == id }
                        textViewTitle.text = taskContent?.titlePersonalTask
                        textViewDescription.text = taskContent?.descriptionPersonalTask
                        textViewStatus.text =
                            getStatusByNum(taskContent!!.statusPersonTask).toString()
                    }
                }
            },
            onFailure = {
                Log.i("allTasks", "fail $it")

            }

        )
    }

    private fun getTask() {
        if (isPersonalType == true) {
            idTask?.let { getPersonalTaskById(it) }
        } else {
            idTask?.let { getTeamTaskById(it) }
        }
    }

    private fun getStatusByNum(num: Int): String {
        return when (num) {
            0 -> getString(R.string.status_todo)
            1 -> getString(R.string.status_inprogress)
            2 -> getString(R.string.status_done)
            else -> "unknown"
        }
    }

    private fun initializePreferences(context: Context) = preferences.initPreferences(context)
    companion object {
        private const val TASK_ID = "Task Id"
        private const val IS_PERSONAL = "Is_Personal"
        fun newInstance(taskId: String, isPersonal: Boolean) =
            TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL, isPersonal)
                    putString(TASK_ID, taskId)
                }
            }
    }
}