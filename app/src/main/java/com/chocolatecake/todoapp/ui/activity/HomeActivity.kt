package com.chocolatecake.todoapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chocolatecake.todoapp.R

import com.chocolatecake.todoapp.databinding.ActivityHomeBinding
import com.chocolatecake.todoapp.ui.register.RegistrationFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val registerFragment = RegistrationFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialFragment()
    }

    private fun initialFragment(){
        val transition = supportFragmentManager.beginTransaction()
        transition.add(R.id.fragment_container_view, registerFragment)
        transition.commit()
    }
}