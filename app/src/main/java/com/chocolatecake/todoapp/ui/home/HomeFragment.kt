package com.chocolatecake.todoapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.network.services.personal.PersonalTaskService
import com.chocolatecake.todoapp.data.network.services.team.TeamTaskService
import com.chocolatecake.todoapp.databinding.FragmentHomeBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private lateinit var sharedPreferences: TaskSharedPreferences
    var isPersonal = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = TaskSharedPreferences()
        sharedPreferences.initPreferences(requireContext())
        addCallBacks()


    }

    private fun addCallBacks() {

        binding.floatingActionButton.setOnClickListener {
//            val addNewTaskFragment = AddNewTaskFragment.newInstance(true)
//            replaceFragment(addNewTaskFragment)
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.apply {
                    when (tab?.position) {
                        0 -> {
                            Toast.makeText(requireContext(), "t", Toast.LENGTH_SHORT).show()
                            fetchTeamTask()

                        }
                        1 -> {
                            Toast.makeText(requireContext(), "p", Toast.LENGTH_SHORT).show()
                            fetchPersonTask()
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

    private fun onCreateTaskSuccess(body: String?) {
        createToast("task created successfully")
    }

    private fun onCreateTaskFailure(message: String?) {
        createToast("error occurred")
    }

    private fun fetchTeamTask() {
        val teamTaskService = TeamTaskService(sharedPreferences)
        teamTaskService.getAllTasks(
            ::onCreateTaskFailure, ::onCreateTaskSuccess
        )
    }

    private fun fetchPersonTask() {
        val personTaskService = PersonalTaskService(sharedPreferences)
        personTaskService.getAllTasks(
            ::onCreateTaskFailure, ::onCreateTaskSuccess
        )
    }

    private fun checkUserSection(section: Int): Boolean {


        return if (section == IS_PERSONAL) {
            true
        } else if (section == IS_TEAM) {
            false
        } else false

    }


    companion object {
        val IS_TEAM = 1
        val IS_PERSONAL = 2
    }

    private fun createToast(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}