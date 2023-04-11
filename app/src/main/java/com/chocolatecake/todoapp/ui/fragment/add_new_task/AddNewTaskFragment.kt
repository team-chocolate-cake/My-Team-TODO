package com.chocolatecake.todoapp.ui.fragment.add_new_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentAddNewTaskBinding
import com.chocolatecake.todoapp.ui.fragment.base.BaseFragment

class AddNewTaskFragment : BaseFragment<FragmentAddNewTaskBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddNewTaskBinding
        get() = FragmentAddNewTaskBinding::inflate

    private val sharedPreferences = TaskSharedPreferences()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCallBacks()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun addCallBacks() {
        binding.buttonAdd.setOnClickListener {
            if (inputFieldsAreVaild()) {
                pushTaskToApi()
            } else {
                showInputsNotValidMessage()
            }
        }
    }

    private fun showInputsNotValidMessage() {
        createToast("please fill all fields")
    }

    private fun inputFieldsAreVaild(): Boolean {
        binding.apply {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val assignee = editTextAssignee.text.toString().trim()
            return title.isNotEmpty() &&
                    description.isNotEmpty() &&
                    assignee.isNotEmpty()
        }
    }

    private fun pushTaskToApi() {
        if (true) {
            createTeamTask()
        } else {
            createPersonalTask()
        }
    }

    private fun createPersonalTask() {
        val personalTaskService = PersonalTaskService(sharedPreferences)
        val personalTaskRequest = createPersonalTaskRequestObject()
        personalTaskService.createTask(
            personalTaskRequest,
            ::onCreateTaskSuccess,
            ::onCreateTaskFailure
        )

    }

    private fun createPersonalTaskRequestObject(): PersonalTaskRequest {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        return PersonalTaskRequest(title, description)
    }

    private fun createTeamTask() {
        val teamTaskService = TeamTaskService(sharedPreferences)
        val teamTaskRequest = createTeamTaskRequestObject()
        teamTaskService.createTask(teamTaskRequest, ::onCreateTaskSuccess, ::onCreateTaskFailure)
    }

    private fun onCreateTaskSuccess(body: String?) {
        createToast("task created successfully")
    }

    private fun createToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onCreateTaskFailure(message: String?) {
        createToast("error occurred")
    }

    private fun createTeamTaskRequestObject(): TeamTaskRequest {
        val title = binding.editTextTitle.text.toString().trim()
        val assignee = binding.editTextAssignee.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        return TeamTaskRequest(title, description, assignee)
    }


}