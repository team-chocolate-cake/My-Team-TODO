package com.chocolatecake.todoapp.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.databinding.FragmentHomeBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.adapter.HomeAdapter
import com.chocolatecake.todoapp.ui.home.model.HomeItem
import com.chocolatecake.todoapp.ui.home.model.SearchQuery
import com.chocolatecake.todoapp.ui.home.model.Status
import com.chocolatecake.todoapp.ui.home.presenter.HomePresenter
import com.chocolatecake.todoapp.ui.home.utils.toHomeItem
import com.chocolatecake.todoapp.ui.login.LoginFragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val presenter by lazy { HomePresenter(this, requireContext()) }
    private lateinit var homeAdapter: HomeAdapter
    private var searchQuery: SearchQuery = SearchQuery()
    private var isPersonal = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        addCallBacks()
    }

    private fun setup() {
        presenter.getTeamTask(getSelectedChips())
        presenter.getTeamStatusListCount()
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
                            presenter.getTeamTask(getSelectedChips())
                            presenter.getTeamStatusListCount()
                            isPersonal = false
                        }
                        PERSONAL_POSITION -> {
                            presenter.getPersonalTask(getSelectedChips())
                            presenter.getPersonalStatusListCount()
                            isPersonal = true
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.editTextSearch.addTextChangedListener {
            searchQuery = searchQuery.copy(
                title = it.toString(),
                status = getSelectedChips()
            )
            if (isPersonal) {
                presenter.searchPersonalTasks(searchQuery)
            } else {
                presenter.searchTeamTasks(searchQuery)
            }
        }
        binding.chipGroup.setOnCheckedStateChangeListener { _, _ ->
            searchQuery = searchQuery.copy(status = getSelectedChips())
            if (isPersonal) {
                presenter.searchPersonalTasks(searchQuery)
            } else {
                presenter.searchTeamTasks(searchQuery)
            }
        }
    }

    private fun getSelectedChips(): List<Status> {
        val statusList = mutableListOf<Status>()
        binding.chipGroup.forEachIndexed { index, view ->
            if ((view as Chip).isChecked) {
                statusList.add(Status.createStatus(index))
            }
        }
        return statusList.toList()
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

    override fun onTeamTasksSuccess(teamTasks: List<TeamTask>) {
        runOnUi {
            setUpTeamTasksRecyclerView(teamTasks)
        }
    }

    override fun onPersonalTasksSuccess(personalTasks: List<PersonalTask>) {
        runOnUi {
            setUpPersonalTasksRecyclerView(personalTasks)
        }
    }

    override fun onUnauthorizedResponse() {
        replaceFragment(LoginFragment())
    }

    override fun onSearchTeamResultSuccess(teamTasks: List<TeamTask>) {
        runOnUi {
            val itemsList: MutableList<HomeItem> = mutableListOf()
            itemsList.addAll(teamTasks.map { it.toHomeItem() })
            homeAdapter.updateList(itemsList)
        }
    }

    override fun onSearchPersonalResultSuccess(personalTasks: List<PersonalTask>) {
        val itemsList: MutableList<HomeItem> = mutableListOf()
        itemsList.addAll(personalTasks.map { it.toHomeItem() })
        runOnUi { homeAdapter.updateList(itemsList) }
    }

    override fun onStatusCountsSuccess(statusList: Triple<Int?, Int?, Int?>) {
        runOnUi { updateChipsStatus(statusList) }
    }

    private fun updateChipsStatus(tasksCount: Triple<Int?, Int?, Int?>) {
        with(binding) {
            chipGroup.children.forEach {
                when (it.id) {
                    R.id.toDoChip -> {
                        toDoChip.text = getString(R.string.to_do_task, tasksCount.first)
                    }
                    R.id.InProgressChip -> {
                        InProgressChip.text =
                            getString(R.string.in_progress_task, tasksCount.second)
                    }
                    R.id.DoneChip -> {
                        DoneChip.text = getString(R.string.done_task, tasksCount.third)
                    }
                }
            }
        }
    }

    private fun setUpTeamTasksRecyclerView(teamTasks: List<TeamTask>) {
        val itemsList: MutableList<HomeItem> = mutableListOf()
        itemsList.addAll(teamTasks.map { it.toHomeItem() })
        homeAdapter =
            HomeAdapter(itemsList, ::onClickTask, ::onClickTask)
        binding.recyclerView.adapter = homeAdapter
        homeAdapter.updateList(itemsList)
    }


    private fun setUpPersonalTasksRecyclerView(personalTasks: List<PersonalTask>) {
        val itemsList: MutableList<HomeItem> = mutableListOf()
        itemsList.addAll(personalTasks.map { it.toHomeItem() })
        val homeAdapter =
            HomeAdapter(itemsList, ::onClickTask, ::onClickTask)
        binding.recyclerView.adapter = homeAdapter
        homeAdapter.updateList(itemsList)
    }

    private fun onClickTask(id: String) {
        createToast(id)
    }

    private fun createToast(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun runOnUi(runner: () -> Unit) = activity?.runOnUiThread { runner() }

    private companion object {
        const val TEAM_POSITION = 0
        const val PERSONAL_POSITION = 1
    }
}