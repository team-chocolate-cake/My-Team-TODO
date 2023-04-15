package com.chocolatecake.todoapp.core.util
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.chocolatecake.todoapp.R
import com.google.android.material.snackbar.Snackbar

fun FragmentActivity.showSnackbar(message: String?, view: View){
    Snackbar.make(view, message.toString(), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.ok)) {}
        .show()
}