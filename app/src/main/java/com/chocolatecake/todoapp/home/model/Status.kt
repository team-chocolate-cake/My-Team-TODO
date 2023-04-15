package com.chocolatecake.todoapp.home.model

enum class Status(val status: Int) {
    TODO(0),
    PROGRESS(1),
    DONE(2);

    companion object {
        fun createStatus(status: Int): Status {
            return when (status) {
                0 -> TODO
                1 -> PROGRESS
                else -> DONE
            }
        }
    }
}