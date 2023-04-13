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

    private val sharedPreferences by lazy {
        TaskSharedPreferences().also {
            it.initPreferences(requireContext())
            it.token = ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isPersonal = retrieveTypeFromArguments()
        setPersonalOrTeamLayout(isPersonal)
        addCallBacks()
    }

    private fun showBottomSheetDialog() {
        CreateTaskConfirmDialog(::createTask)
            .show(childFragmentManager, "newTaskTag")
    }

    private fun addCallBacks() {
        binding.buttonAdd.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.appbar.setNavigationOnClickListener {
            returnToHomeFragment()
        }
    }

    private fun createTask() {
        if (isInputValid()) {
            pushTaskToApi()
            returnToHomeFragment()
        }
    }

    private fun returnToHomeFragment() {
        parentFragmentManager.popBackStack()
    }

    private fun setPersonalOrTeamLayout(isPersonal: Boolean) {
        binding.apply {
            if (isPersonal) {
                removeAssigneeInputField()
            } else {
                appbar.title = resources.getString(R.string.title_add_new_team_task)
                showAssigneeInputField()
            }
        }
    }

    private fun showAssigneeInputField() {
        binding.apply {
            editTextAssignee.visibility = View.VISIBLE
            textViewAssignee.visibility = View.VISIBLE
        }
    }

    private fun removeAssigneeInputField() {
        binding.apply {
            editTextAssignee.visibility = View.GONE
            textViewAssignee.visibility = View.GONE
        }
    }

    private fun isInputValid(): Boolean {
        val isPersonal = retrieveTypeFromArguments()
        return if (isPersonal) {
            isPersonalInputValid()
        } else {
            isTeamInputValid()
        }
    }

    private fun isPersonalInputValid(): Boolean {
        binding.apply {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            if (title.isEmpty() || description.isEmpty()) {
                createToast(getString(R.string.please_fill_fields))
                return false
            }
        }

        return true
    }

    private fun isTeamInputValid(): Boolean {
        binding.apply {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val assignee = editTextAssignee.text.toString().trim()
            if (title.isEmpty() || description.isEmpty() || assignee.isEmpty()) {
                createToast(getString(R.string.please_fill_fields))
                return false
            }
        }
        return true
    }

    private fun pushTaskToApi() {
        if (retrieveTypeFromArguments()) {
            createPersonalTask()
        } else {
            createTeamTask()
        }
    }

    private fun createPersonalTask() {
        val personalTaskRequest = createPersonalTaskRequestObject()
        val personalTaskService = PersonalTaskService(sharedPreferences)
        personalTaskService.createTask(
            personalTaskRequest,
            onFailure = ::onCreateTaskFailure,
            onSuccess = ::onCreateTaskSuccess
        )
    }

    private fun createTeamTask() {
        val teamTaskService = TeamTaskService(sharedPreferences)
        val teamTaskRequest = createTeamTaskRequestObject()
        teamTaskService.createTask(
            teamTaskRequest, onFailure = ::onCreateTaskFailure,
            onSuccess = ::onCreateTaskSuccess
        )
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

    private fun retrieveTypeFromArguments(): Boolean {
        return arguments?.getBoolean(IS_PERSONAL, true)!!
    }

    private fun createToast(message: String?) {
        activity?.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val IS_PERSONAL = "ARE_WE_ADDING_TO_PERSONAL_TASKS"
        fun newInstance(isPersonal: Boolean) =
            AddNewTaskFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL, isPersonal)
                }
            }
    }
}