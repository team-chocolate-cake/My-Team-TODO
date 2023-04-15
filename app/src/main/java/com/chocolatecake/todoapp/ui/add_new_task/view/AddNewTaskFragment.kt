package com.chocolatecake.todoapp.ui.add_new_task.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.databinding.FragmentAddNewTaskBinding
import com.chocolatecake.todoapp.ui.add_new_task.presenter.AddNewTaskPresenter
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.util.navigateBack
import com.chocolatecake.todoapp.util.showSnackbar

class AddNewTaskFragment : BaseFragment<FragmentAddNewTaskBinding>(), AddNewTaskView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddNewTaskBinding
        get() = FragmentAddNewTaskBinding::inflate

    private val presenter: AddNewTaskPresenter by lazy { AddNewTaskPresenter(requireContext(), this) }
    private val isPersonal: Boolean by lazy { retrieveTypeFromArguments() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentAdd.setOnClickListener {  }
        setPersonalOrTeamLayout(isPersonal)
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonAdd.setOnClickListener {
            if (isInputValid()) {
                showBottomSheetDialog()
            } else {
                activity?.showSnackbar(
                    message = getString(R.string.please_fill_fields),
                    binding.root
                )
            }
        }
        binding.appbar.setNavigationOnClickListener {
            returnToHomeFragment()
        }
    }

    private fun showBottomSheetDialog() {
        CreateTaskConfirmDialog(isPersonal, ::createTask)
            .show(childFragmentManager, BOTTOM_SHEET_DIALOG)
    }

    private fun createTask() {
        createAndPushTaskToApi()
        returnToHomeFragment()
    }

    private fun isInputValid(): Boolean {
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
            return noStringIsEmpty(title, description)
        }
    }

    private fun isTeamInputValid(): Boolean {
        binding.apply {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val assignee = editTextAssignee.text.toString().trim()
            return noStringIsEmpty(title, description, assignee)
        }
    }

    private fun noStringIsEmpty(vararg values: String): Boolean {
        return values.all { it.isNotEmpty() }
    }

    private fun returnToHomeFragment() {
        activity?.navigateBack()
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

    private fun createAndPushTaskToApi() {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val assignee = binding.editTextAssignee.text.toString().trim()

        if (isPersonal) {
            presenter.createPersonalTask(title, description)
        } else {
            presenter.createTeamTask(title, description, assignee)
        }
    }

    private fun retrieveTypeFromArguments(): Boolean {
        return arguments?.getBoolean(IS_PERSONAL, true)!!
    }

    override fun onCreateTaskFailure() {
        activity?.showSnackbar(message = "Failure", binding.root)
    }

    override fun onCreateTaskSuccess() {
        activity?.showSnackbar(message = "Success", binding.root)
    }

    companion object {
        private const val IS_PERSONAL = "IS_PERSONAL"
        private const val BOTTOM_SHEET_DIALOG = "newTaskTag"

        fun newInstance(isPersonal: Boolean) =
            AddNewTaskFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL, isPersonal)
                }
            }
    }
}