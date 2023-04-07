package com.chocolatecake.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.chocolatecake.indianfood.ui.base.BaseActivity
import com.chocolatecake.todoapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val LOG_TAG: String = MAIN_ACTIVITY

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        =ActivityMainBinding::inflate

    override fun setUp() {}

    override fun addCallbacks() {}

    companion object{
        private const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}