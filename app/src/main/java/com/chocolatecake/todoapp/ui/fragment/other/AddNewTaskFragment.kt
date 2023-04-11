package com.chocolatecake.todoapp.ui.fragment.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentAddNewTaskBinding
import com.chocolatecake.todoapp.ui.fragment.base.BaseFragment

class AddNewTaskFragment : BaseFragment<FragmentAddNewTaskBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddNewTaskBinding
        get() = FragmentAddNewTaskBinding::inflate

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
            pushTeamTask()
        }
    }

    private fun pushTeamTask() {
        val teamTaskService = TeamTaskService(TaskSharedPreferences())
        val teamTaskRequest = createTeamTaskRequestObject()

        teamTaskService.createTask(teamTaskRequest,::onCreateTaskSuccess,::onCreateTaskFailure)

    }

    private fun onCreateTaskSuccess(body:String?){
        createToast("task created successfully")
    }

    private fun createToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onCreateTaskFailure(message:String?){
        createToast("error occurred")
    }
    private fun createTeamTaskRequestObject(): TeamTaskRequest {
        val title = binding.editTextTitle.text.toString().trim()
        val assignee = binding.editTextAssignee.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        return TeamTaskRequest(title, description, assignee)
    }


}