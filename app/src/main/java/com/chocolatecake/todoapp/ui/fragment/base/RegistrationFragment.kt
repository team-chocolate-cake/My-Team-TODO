package com.chocolatecake.todoapp.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.util.Constant

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

    private fun isUsernameValid(username: String): String {
        for(i in username.trim().indices){
            if(isSpecial(username[i])) return Constant.ERROR_VALIDATION_USER_NAME_SPECIAL
            else if(isSpace(username[i])) return Constant.ERROR_VALIDATION_USER_NAME_SPACE
            else if(username[0].isDigit()) return Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
            else if(username.length < Constant.VALIDATION_USERNAME_LENGTH)
                return Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
        }
        return ""
    }

    private fun isSpace(char: Char) = char == ' '

    private fun isSpecial(char: Char): Boolean {
        val specialChars = "!@#$%^&*()-+=<>?,./;:'\"[]{}\\|"
        return specialChars.contains(char)
    }

}
