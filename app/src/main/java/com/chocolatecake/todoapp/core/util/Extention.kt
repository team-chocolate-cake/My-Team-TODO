package com.chocolatecake.todoapp.core.util

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

fun View.show() {
    val transition = Fade()
    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    this.visibility = View.VISIBLE
}

fun View.hide() {
    val transition = Fade()
    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    this.visibility = View.GONE
}

fun String.passwordLength(): Boolean {
    return this.trim().length < 8
}

fun String.usernameLength(): Boolean {
    return this.trim().length < 3
}

