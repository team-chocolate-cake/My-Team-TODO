package com.chocolatecake.todoapp.core.util

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun String.passwordLength(): Boolean {
    return this.trim().length < 8

}

fun String.usernameLength(): Boolean {
    return this.trim().length < 3
}

