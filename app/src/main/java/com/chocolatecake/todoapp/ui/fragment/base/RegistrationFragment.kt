package com.chocolatecake.todoapp.ui.fragment.base

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.util.Constant


class RegistrationFragment : Fragment(){
    private lateinit var binding : FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        validationOfPassword()
        return binding.root
    }

    private fun validationOfPassword() {
        binding.apply {
            editTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength < Constant.VALIDATION_PASSWORD_LENGTH -> {
                        textViewValidate.text = Constant.ERROR_VALIDATION_PASSWORD_TEXT
                        textViewValidate.visibility = View.VISIBLE
                    }
                    else -> textViewValidate.visibility = View.GONE
                }
            }
        }
    }
    private fun checkTheRegisterValidation(){

    }
}
