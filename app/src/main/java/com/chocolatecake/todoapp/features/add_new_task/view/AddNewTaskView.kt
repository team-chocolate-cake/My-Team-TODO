package com.chocolatecake.todoapp.features.add_new_task.view

interface AddNewTaskView {
    fun showNoNetworkError()
    fun showAddTaskSuccess()
    fun showError(message: String)
}