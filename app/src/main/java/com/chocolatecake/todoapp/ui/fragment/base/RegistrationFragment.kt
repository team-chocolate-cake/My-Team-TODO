package com.chocolatecake.todoapp.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.util.Constant
import com.chocolatecake.todoapp.util.getUsernameStatus

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var validationUserName : Boolean = false
    private var validationPassword : Boolean = false
    private var validationConfirm : Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        validationOfUsername()
        validationOfPassword()
        matchPassword()
        checkValidationRegister()
        return binding.root
    }
    private fun validationOfUsername() {
        binding.apply {
            editTextUsername.addTextChangedListener {
                val editTextContent =  editTextUsername.text.toString().getUsernameStatus()
                when (editTextContent) {
                    Constant.ERROR_VALIDATION_USER_NAME_SPECIAL -> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_SPECIAL
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    Constant.ERROR_VALIDATION_USER_NAME_SPACE -> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_SPACE
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT -> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT -> {
                        textViewValidateUserName.text = Constant.ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    else -> {
                        textViewValidateUserName.visibility = View.GONE
                        validationUserName = true
                    }
                }
            }
        }
    }
    private fun validationOfPassword() {
        binding.apply {
            editTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength < Constant.VALIDATION_PASSWORD_LENGTH -> {
                        textViewValidatePassword.text = Constant.ERROR_VALIDATION_PASSWORD_TEXT_LENGTH
                        textViewValidatePassword.visibility = View.VISIBLE
                        validationPassword =  false
                    }
                    else -> {
                        textViewValidatePassword.visibility = View.GONE
                        validationPassword =  true
                    }
                }
            }
        }
    }
    private fun matchPassword(){
        binding.apply {
            editTextConfirmPassword.addTextChangedListener {confirmPassword ->
                if(editTextPassword.text.toString() == editTextConfirmPassword.text.toString()){
                    textViewValidateConfirm.visibility = View.GONE
                    validationConfirm = true
                }
                else{
                    textViewValidateConfirm.text = Constant.ERROR_VALIDATION_CONFIRM_PASSWORD_MISMATCH
                    textViewValidateConfirm.visibility = View.VISIBLE
                    validationConfirm = false
                }
            }
        }
    }
    private fun checkValidationRegister(){
        binding.buttonRegister.setOnClickListener {
            if(validationUserName && validationPassword && validationConfirm){
                val auth = AuthService(onFailure = {
                    TODO("ايرور")
                }, onSuccess = {
                    navigateToHomeScreen()
                })
                val username = binding.editTextUsername.text.toString()
                val password = binding.editTextPassword.text.toString()
                val newUser = UserRequest(username, password)
                auth.register(newUser)
            }
        }
    }
    private fun navigateToHomeScreen(){}
    private fun navigateToLoginScreen(){}

}


