package com.chocolatecake.todoapp.ui.fragment.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.response.TeamTaskResponse
import com.chocolatecake.todoapp.data.model.response.TeamTasksResponse
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentTaskDetailsBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.google.gson.Gson

class TaskDetailsFragment() : BaseFragment<FragmentTaskDetailsBinding>() {
    private val preferences by lazy { TaskSharedPreferences() }
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTaskDetailsBinding =
        FragmentTaskDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePreferences(requireContext())
        preferences.token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImUxZDg4OTlkLWUxMmMtNGZmMS1hMjNiLTA3MjMzYTIyNjYzNyIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNDk1OTE2fQ.ETN-68eaYCheMeWry6DdAkcVYXhZhAg8cKRVmCu9_Lw"
        getTaskById(requireContext(), "4559e4d7-27e5-4404-b757-a4f783400654")

    }

    private fun getTaskById(context: Context, id: String) {


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
                        textViewStatus.text = getStatusByNum(taskContent!!.statusTeamTask).toString()
                    }
                }
            },
            onFailure = {
                Log.i("allTasks", "fail $it")
            }

        )
    }

    private fun getStatusByNum(num: Int):String {
        return when (num) {
            0 -> getString(R.string.status_todo)
            1 -> getString(R.string.status_inprogress)
            2 -> getString(R.string.status_done)
            else -> "unkown"
        }
    }

    private fun initializePreferences(context: Context) = preferences.initPreferences(context)

    companion object {
        private const val TASK_ID = "Task Id"
        fun newInstance(taskId: String) =
            TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(TASK_ID, taskId)
                }
            }
    }
}