package com.chocolatecake.todoapp.ui.fragment.add_new_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.PersonalTaskRequest
import com.chocolatecake.todoapp.data.model.request.TeamTaskRequest
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentAddNewTaskBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment

class AddNewTaskFragment : BaseFragment<FragmentAddNewTaskBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddNewTaskBinding
        get() = FragmentAddNewTaskBinding::inflate
    private lateinit var sharedPreferences: TaskSharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeLayout(retrieveTypeFromArguments())
        sharedPreferences = TaskSharedPreferences()
        sharedPreferences.initPreferences(requireContext())
        sharedPreferences.token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjE1NzRhMDA0LWY2ZWEtNGMxNC1iMTRhLTRmY2QwZjZkNzhhMiIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNDMxMDAzfQ.T0GR646YF4eQg1rMQ-kQxkF3VjPS3RwI_0jCmyIcLVU"
        addCallBacks()
    }
    private fun addCallBacks() {
        binding.buttonAdd.setOnClickListener {
            val isPersonal = retrieveTypeFromArguments()
            checkInputField(isPersonal)
            pushTaskToApi()
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.appbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    private fun customizeLayout(isPersonal: Boolean) {
        binding.apply {
            if (isPersonal) {
                editTextAssignee.visibility = View.GONE
                textViewAssignee.visibility = View.GONE
            } else {
                appbar.title = resources.getString(R.string.title_add_new_team_task)
                editTextAssignee.visibility = View.VISIBLE
                textViewAssignee.visibility = View.VISIBLE
            }
        }
    }
    private fun checkInputField(isPersonal: Boolean) {
        if (!isPersonal) {
            binding.apply {
                val title = editTextTitle.text.toString().trim()
                val description = editTextDescription.text.toString().trim()
                val assignee = editTextAssignee.text.toString().trim()
                if (title.isEmpty() || description.isEmpty() && assignee.isEmpty()) {
                    createToast("please fill all fields")
                }
            }
        } else {
            binding.apply {
                val title = editTextTitle.text.toString().trim()
                val description = editTextDescription.text.toString().trim()
                if (title.isEmpty() || description.isEmpty()) {
                    createToast("please fill all fields")
                }
            }
        }
    }

    private fun pushTaskToApi() {
        if (retrieveTypeFromArguments()) {
            createToast(retrieveTypeFromArguments().toString())
            createPersonalTask()
        } else {
            createToast(retrieveTypeFromArguments().toString())
            createTeamTask()
        }
    }

    private fun createPersonalTask() {
        val personalTaskService = PersonalTaskService(sharedPreferences)
        val personalTaskRequest = createPersonalTaskRequestObject()
        personalTaskService.createTask(
            personalTaskRequest, onFailure = ::onCreateTaskFailure,
            onSuccess = ::onCreateTaskSuccess
        )
    }
    private fun createTeamTask() {
        val teamTaskService = TeamTaskService(sharedPreferences)
        val teamTaskRequest = createTeamTaskRequestObject()
        teamTaskService.createTask(teamTaskRequest, onFailure = ::onCreateTaskFailure,
            onSuccess = ::onCreateTaskSuccess)
    }
    private fun createPersonalTaskRequestObject(): PersonalTaskRequest {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        return PersonalTaskRequest(title, description)
    }
    private fun createTeamTaskRequestObject(): TeamTaskRequest {
        val title = binding.editTextTitle.text.toString().trim()
        val assignee = binding.editTextAssignee.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        return TeamTaskRequest(title, description, assignee)
    }
    private fun onCreateTaskSuccess(body: String?) {
        createToast(body)
    }
    private fun onCreateTaskFailure(message: String?) {
        createToast(message)
    }
    companion object {
        const val IS_PERSONAL = true
        fun newInstance(isPersonal: Boolean) =
            AddNewTaskFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL.toString(), isPersonal)
                }
            }
    }
    private fun retrieveTypeFromArguments(): Boolean {
        return arguments?.getBoolean(IS_PERSONAL.toString(), true)!!
    }
    private fun createToast(message: String?) {
       activity?.runOnUiThread {
           Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
       }
    }
}