package com.chocolatecake.todoapp.core.util

import android.app.Activity
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

fun View.show() {
    val activity = context as Activity
    activity.runOnUiThread {
        val transition = Fade()
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        this.visibility = View.VISIBLE
    }
}

fun View.hide() {
    val activity = context as Activity
    activity.runOnUiThread {
        val transition = Fade()
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        this.visibility = View.GONE
    }
}