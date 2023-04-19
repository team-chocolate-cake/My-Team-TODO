package com.chocolatecake.todoapp.features.task_details.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.databinding.FragmentTaskDetailsBinding
import com.chocolatecake.todoapp.features.base.fragment.BaseFragment
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.response.PersonalTask
import com.chocolatecake.todoapp.core.data.model.response.TeamTask
import com.chocolatecake.todoapp.features.task_details.presenter.TaskDetailsPresenter
import com.chocolatecake.todoapp.core.util.navigateBack
import com.chocolatecake.todoapp.core.util.showSnackbar

class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding>(), TaskDetailsView {
    private val isPersonal by lazy {
        arguments?.getBoolean(IS_PERSONAL, true)!!
    }
    private val taskDetailsPresenter: TaskDetailsPresenter by lazy {
        TaskDetailsPresenter(TaskSharedPreferences(requireActivity().applicationContext))
    }

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTaskDetailsBinding =
        FragmentTaskDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskDetailsPresenter.taskDetailsView = this
        binding.fragmentDetails.setOnClickListener { }
        getTask()
        addCallBack()
    }

    private fun getTask() {
        val args = arguments
        if (isPersonal) {
            val personalResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(PERSONAL_TASK_OBJECT)
                } else {
                    args?.getParcelable(PERSONAL_TASK_OBJECT) as? PersonalTask
                }
            showPersonalTaskData(personalResult)
        } else {
            val teamResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(TEAM_TASK_OBJECT)
                } else {
                    args?.getParcelable(TEAM_TASK_OBJECT) as? TeamTask
                }
            showTeamTaskData(teamResult)
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

    private fun addCallBack() {
        binding.appbar.setNavigationOnClickListener {
            activity?.navigateBack()
        }
        binding.textViewStatus.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val args = arguments
        if (isPersonal) {
            val personalResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(PERSONAL_TASK_OBJECT)
                } else {
                    args?.getParcelable(PERSONAL_TASK_OBJECT) as? PersonalTask
                }
            ChangeStatusBottomSheet(::updateStatus, personalResult!!.status)
                .show(childFragmentManager, BOTTOM_SHEET_DIALOG)
        } else {
            val teamResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(TEAM_TASK_OBJECT)
                } else {
                    args?.getParcelable(TEAM_TASK_OBJECT) as? TeamTask
                }
            ChangeStatusBottomSheet(::updateStatus, teamResult!!.status)
                .show(childFragmentManager, BOTTOM_SHEET_DIALOG)
        }
    }

    private fun updateStatus(status: Int) {
        val args = arguments
        if (isPersonal) {
            val personalResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(PERSONAL_TASK_OBJECT)
                } else {
                    args?.getParcelable(PERSONAL_TASK_OBJECT) as? PersonalTask
                }
            taskDetailsPresenter.updatePersonalStatus(personalResult!!.id, status)
        } else {
            val teamResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(TEAM_TASK_OBJECT)
                } else {
                    args?.getParcelable(TEAM_TASK_OBJECT) as? TeamTask
                }
            taskDetailsPresenter.updateTeamStatus(teamResult!!.id, status)
        }

    }

    override fun showTeamTaskData(result: TeamTask?) {
        requireActivity().runOnUiThread {
            with(binding) {
                textViewTitle.text = result?.title
                textViewAssignee.text = result?.assignee
                textViewDescription.text = result?.description
                textViewDate.text = result?.creationTime?.let { getDate(it) }
                textViewStatus.text = result?.status?.let { getStatusByNum(it) }
            }
        }
    }

    override fun showPersonalTaskData(result: PersonalTask?) {
        requireActivity().runOnUiThread {
            with(binding) {
                textViewTitle.text = result?.title
                textViewDescription.text = result?.description
                textViewDate.text = result?.creationTime?.let { getDate(it) }
                textViewStatus.text = result?.status?.let { getStatusByNum(it) }
            }
        }
    }

    private fun getDate(creationTime: String): String {
        return creationTime.split("T").first()
    }

    override fun showFailedStatusUpdate() {
        activity?.showSnackbar(getString(R.string.failed_update), binding.root)
    }

    override fun showUpdatedStatus(status: Int) {
        activity?.run {
            showSnackbar(getString(R.string.success_update), binding.root)
            runOnUiThread {
                binding.textViewStatus.text = getStatusByNum(status)
            }
        }
    }

    companion object {
        private const val TEAM_TASK_OBJECT = "Team_Task"
        private const val PERSONAL_TASK_OBJECT = "Personal_Task"
        private const val IS_PERSONAL = "Is_Personal"
        private const val BOTTOM_SHEET_DIALOG = "bottom sheet status"
        fun newTeamInstance(teamTask: TeamTask) =
            TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL, false)
                    putParcelable(TEAM_TASK_OBJECT, teamTask)
                }
            }


        fun newPersonalInstance(personalTask: PersonalTask) =
            TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL, true)
                    putParcelable(PERSONAL_TASK_OBJECT, personalTask)
                }
            }

    }
}