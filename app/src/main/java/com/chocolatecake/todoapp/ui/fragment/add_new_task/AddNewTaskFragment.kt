package com.chocolatecake.todoapp.ui.fragment.add_new_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.databinding.FragmentAddNewTaskBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment

class AddNewTaskFragment : BaseFragment<FragmentAddNewTaskBinding>(), AddNewTaskView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddNewTaskBinding
        get() = FragmentAddNewTaskBinding::inflate

    private lateinit var presenter: AddNewTaskPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeLayout(retrieveTypeFromArguments())
        presenter = AddNewTaskPresenter(requireContext(), this)
        setUp()
    }
    private fun setUp() {
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
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val assignee = binding.editTextAssignee.text.toString().trim()
        if (retrieveTypeFromArguments()) {
            presenter.createPersonalTask(title, description)
        } else {
            presenter.createTeamTask(title, description, assignee)
        }
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
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateTaskFailure() {
        createToast("Failure")
    }
    override fun onCreateTaskSuccess() {
        createToast("Success")
    }
}