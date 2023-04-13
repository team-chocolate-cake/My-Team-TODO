package com.chocolatecake.todoapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chocolatecake.todoapp.R


enum class NavigationState {
    ADD,
    REPLACE,
    REMOVE,
}


fun FragmentActivity.navigateTo(to: Fragment) {
    changeNavigation(this, NavigationState.ADD, to)
}

fun FragmentActivity.navigateExclusive(to: Fragment) {
    changeNavigation(this, NavigationState.REPLACE, to)
}

fun FragmentActivity.navigateBack() {
    changeNavigation(this, NavigationState.REMOVE, null)
}

private fun changeNavigation(activity: FragmentActivity, state: NavigationState, to: Fragment?) {
    val fragmentManager = activity.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()

    when (state) {
        NavigationState.ADD -> {
            transaction.add(R.id.fragment_container_view, to!!)
            transaction.addToBackStack("fragment")
        }

        NavigationState.REMOVE -> {
            fragmentManager.popBackStack()
        }

        NavigationState.REPLACE -> {
            transaction.replace(R.id.fragment_container_view, to!!)
        }
    }
    transaction.commit()
}

/**
 Example
 requireActivity().navigateExclusive((HomeFragment()))
 requireActivity().navigateTo(TaskFragment.newInstance(id))
*/