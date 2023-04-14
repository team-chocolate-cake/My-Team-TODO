package com.chocolatecake.todoapp.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.databinding.FragmentHomeBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.model.HomeItemType
import com.chocolatecake.todoapp.ui.home.presenter.HomePresenter
import com.chocolatecake.todoapp.ui.home.utils.toHomeItem
import com.chocolatecake.todoapp.ui.login.LoginFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val presenter by lazy { HomePresenter(this, requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
        presenter.getTeamTask(setOf(0))

    }

    private fun addCallBacks() {

        binding.floatingActionButton.setOnClickListener {
//            val addNewTaskFragment = AddNewTaskFragment.newInstance(true)
//            replaceFragment(addNewTaskFragment)
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.apply {
                    when (tab.position) {
                        TEAM_POSITION -> {
                            presenter.getTeamTask(setOf(0))
                        }
                        PERSONAL_POSITION -> {

//                            presenter.getPersonalTask()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onAllTasksFailure(message: String?) {

        createToast(message)
    }

    override fun onTeamTasksSuccess(teamTasks: List<TeamTask>?) {
        activity?.runOnUiThread {
            teamTasks?.let { setUpTeamTasksRecyclerView(it) }
        }
    }

    override fun onPersonalTasksSuccess(personalTasks: List<PersonalTask>?) {

    }

    override fun onUnauthorizedResponse() {
        replaceFragment(LoginFragment())
    }

    private fun setUpTeamTasksRecyclerView(teamTasks: List<TeamTask>) {
        Log.e("mine", teamTasks.toString())
        val tasksCount = getTasksCount(teamTasks)
        val itemsList: MutableList<HomeItem<Any>> = mutableListOf()
        itemsList.add(HomeItem(tasksCount, HomeItemType.TYPE_FILTERS))
           itemsList.addAll(teamTasks.map { it.toHomeItem() })
        // itemsList.add(HomeItem(teamTasks.first(), HomeItemType.TYPE_TASKS))
//        val itemtask= TeamTask(
//            idTeamTask = "e8f2cd95 - a3fb - 41f d -8686 - 031e3 c8af12c",
//            titleTeamTask = "team",
//            descriptionTeamTask = "team",
//            assignee = "assignee",
//            statusTeamTask = 0,
//            creationTime = "2023 - 04 - 10 T15 :31:36.008152")
//        itemsList.add(HomeItem(itemtask , HomeItemType.TYPE_TASKS))
        val homeAdapter = HomeAdapter(itemsList, ::onClickTask)
        binding.recyclerView.adapter = homeAdapter

        homeAdapter.notifyDataSetChanged()
    }

    private fun getTasksCount(teamTasks: List<TeamTask>): List<Int> {
        val toDoTasksCount = teamTasks.count { it.statusTeamTask == HomeAdapter.TO_DO_STATUS }
        val inProgressTasksCount =
            teamTasks.count { it.statusTeamTask == HomeAdapter.IN_PROGRESS_STATUS }
        val doneTasksCount = teamTasks.count { it.statusTeamTask == HomeAdapter.DONE_STATUS }

        return listOf(toDoTasksCount, inProgressTasksCount, doneTasksCount)
    }

    private fun onClickTask(id: String) {

    }

    private fun createToast(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val TEAM_POSITION = 0
        val PERSONAL_POSITION = 1

    }


}