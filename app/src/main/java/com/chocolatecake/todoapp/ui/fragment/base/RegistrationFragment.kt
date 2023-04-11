package com.chocolatecake.todoapp.ui.fragment.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.util.Constant
import com.chocolatecake.todoapp.util.isUsernameValid

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        validationOfUsername()
        validationOfPassword()
        matchPassword()
        return binding.root
    }
    private fun validationOfPassword() {
        binding.apply {
            editTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength < Constant.VALIDATION_PASSWORD_LENGTH -> {
                        textViewValidatePassword.text = Constant.ERROR_VALIDATION_PASSWORD_TEXT_LENGTH
                        textViewValidatePassword.visibility = View.VISIBLE
                    }
                    else -> textViewValidatePassword.visibility = View.GONE
                }
            }
        }
    }
    private fun validationOfUsername(){
        binding.apply {
            editTextUsername.addTextChangedListener { editable->
                when{
                    isUsernameValid(editTextUsername.text.toString()) == Constant.ERROR_VALIDATION_USER_NAME_SPECIAL-> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_SPECIAL
                        textViewValidateUserName.visibility = View.VISIBLE
                    }
                    isUsernameValid(editTextUsername.text.toString()) == Constant.ERROR_VALIDATION_USER_NAME_SPACE-> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_SPACE
                        textViewValidateUserName.visibility = View.VISIBLE
                    }
                    isUsernameValid(editTextUsername.text.toString()) == Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT-> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
                        textViewValidateUserName.visibility = View.VISIBLE
                    }
                    isUsernameValid(editTextUsername.text.toString()) == Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT -> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
                        textViewValidateUserName.visibility = View.VISIBLE
                    }

                    else -> textViewValidateUserName.visibility = View.GONE
                }
            }
        }
    }

    private fun matchPassword(){
        binding.apply {
            editTextConfirmPassword.addTextChangedListener {confirmPassword ->
                if(editTextPassword.text.toString() == editTextConfirmPassword.text.toString())
                    textViewValidateConfirm.visibility = View.GONE
                else{
                    textViewValidateConfirm.text = Constant.ERROR_VALIDATION_CONFIRM_PASSWORD_MISMATCH
                    textViewValidateConfirm.visibility = View.VISIBLE
                }

            }
        }
    }


}
