package com.chocolatecake.todoapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chocolatecake.todoapp.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}