package com.chocolatecake.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.util.navigateExclusive
import com.chocolatecake.todoapp.features.register.RegisterFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val preferences  = TaskSharedPreferences(applicationContext)

        if(preferences.token.isNullOrBlank()){
            navigateToRegisterFragment()
        }
    }
    private fun navigateToRegisterFragment(){
        navigateExclusive(RegisterFragment())
    }
}