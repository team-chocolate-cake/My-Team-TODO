package com.chocolatecake.todoapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        sharedPreferences.token = "J0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjYyNzdkNzMzLTRkN2ItNDZkZS1hMDNmLWI5MGViODEzYWQwYSIsInRlYW1JZCI6IjdjMzBhMDQwLTFiYWQtNDk2Ni1hN2YxLTZhZjk4ZGMzZmFiMyIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNDgwNjQxfQ.MHFN4S1Pu2ax2CoVzPDs61cGVQ-SEoiZVQAo8-C2yBQ"
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
                    when (tab.position) {
                        TEAM_POSITION -> {
                            //getTeamTask()
                        }
                        PERSONAL_POSITION -> {

                            //getPersonalTask()
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

    private fun onCreateTasksSuccess(body: String?) {
        createToast("task created successfully")
    }

    private fun onCreateTasksFailure(message: String?) {
        createToast("error occurred")
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