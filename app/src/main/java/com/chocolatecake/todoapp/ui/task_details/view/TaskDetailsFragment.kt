package com.chocolatecake.todoapp.ui.task_details.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.databinding.FragmentTaskDetailsBinding
import com.chocolatecake.todoapp.ui.add_new_task.view.AddNewTaskFragment
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.task_details.presenter.TaskDetailsPresenter
import com.chocolatecake.todoapp.util.navigateBack
import com.chocolatecake.todoapp.util.showSnackbar

class TaskDetailsFragment() : BaseFragment<FragmentTaskDetailsBinding>(), TaskDetailsView {
    private val isPersonal by lazy {
        arguments?.getBoolean(IS_PERSONAL, true)!!
    }
    private val taskDetailsPresenter: TaskDetailsPresenter by lazy {
        TaskDetailsPresenter(requireContext())
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
    /* private fun getTask() {
         if (isPersonal) {
             val personalResult =
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                     arguments?.getParcelable(PERSONAL_TASK_OBJECT, PersonalTask::class.java)
                 } else {
                     TODO("VERSION.SDK_INT < TIRAMISU")
                 }
             setDataPersonal(personalResult)
         } else {
             val teamResult =
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                     arguments?.getParcelable(TEAM_TASK_OBJECT, TeamTask::class.java)
                 } else {
                     TODO("VERSION.SDK_INT < TIRAMISU")
                 }
             setDataTeam(teamResult)
         }
     }*/

    private fun getTask() {
        val args = arguments
        if (isPersonal) {
            val personalResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(PERSONAL_TASK_OBJECT)
                } else {
                    args?.getParcelable(PERSONAL_TASK_OBJECT) as? PersonalTask
                }
            setDataPersonal(personalResult)
        } else {
            val teamResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(TEAM_TASK_OBJECT)
                } else {
                    args?.getParcelable(TEAM_TASK_OBJECT) as? TeamTask
                }
            setDataTeam(teamResult)
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
            ChangeStatusBottomSheet(::updateStatus, personalResult!!.statusPersonalTask)
                .show(childFragmentManager, BOTTOM_SHEET_DIALOG)
        } else {
            val teamResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(TEAM_TASK_OBJECT)
                } else {
                    args?.getParcelable(TEAM_TASK_OBJECT) as? TeamTask
                }
            ChangeStatusBottomSheet(::updateStatus, teamResult!!.statusTeamTask)
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
            taskDetailsPresenter.updatePersonalStatus(personalResult!!.idPersonalTask, status)
        } else {
            val teamResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    args?.getParcelable(TEAM_TASK_OBJECT)
                } else {
                    args?.getParcelable(TEAM_TASK_OBJECT) as? TeamTask
                }
            taskDetailsPresenter.updateTeamStatus(teamResult!!.idTeamTask, status)
        }

    }

    override fun setDataTeam(result: TeamTask?) {
        requireActivity().runOnUiThread {
            with(binding) {
                textViewTitle.text = result?.titleTeamTask
                textViewAssignee.text = result?.assignee
                textViewDescription.text = result?.descriptionTeamTask
                textViewStatus.text = result?.statusTeamTask?.let { getStatusByNum(it) }
            }
        }
    }

    override fun setDataPersonal(result: PersonalTask?) {
        requireActivity().runOnUiThread {
            with(binding) {
                textViewTitle.text = result?.titlePersonalTask
                textViewDescription.text = result?.descriptionPersonalTask
                textViewStatus.text = result?.statusPersonalTask?.let { getStatusByNum(it) }
            }
        }
    }

    override fun onUpdateFailure() {
        activity?.showSnackbar(getString(R.string.failed_update), binding.root)
    }

    override fun onUpdateSuccess(status: Int) {
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