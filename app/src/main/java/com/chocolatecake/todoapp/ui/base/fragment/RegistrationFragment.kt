package com.chocolatecake.todoapp.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.util.getUsernameStatus

class RegistrationFragment : BaseFragment<FragmentRegisterBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        = FragmentRegisterBinding::inflate
    private var validationUserName : Boolean = false
    private var validationPassword : Boolean = false
    private var validationConfirm : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUsernameValidation()
        setPasswordValidation()
        validatePasswordMatch()
        registerButtonClickHandler()
    }

    private fun setupUsernameValidation() {
        binding.apply {
            editTextUsername.addTextChangedListener {
                when (getUsernameStatus(editTextUsername.text.toString())) {
                    ERROR_VALIDATION_USER_NAME_SPECIAL -> {
                        textViewValidateUserName.text = ERROR_VALIDATION_USER_NAME_SPECIAL
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    ERROR_VALIDATION_USER_NAME_SPACE -> {
                        textViewValidateUserName.text = ERROR_VALIDATION_USER_NAME_SPACE
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT -> {
                        textViewValidateUserName.text = ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT
                        textViewValidateUserName.visibility = View.VISIBLE
                        validationUserName = false
                    }
                    ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT -> {
                        textViewValidateUserName.text = ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT
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
    private fun setPasswordValidation() {
        binding.apply {
            editTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength < VALIDATION_PASSWORD_LENGTH -> {
                        textViewValidatePassword.text = ERROR_VALIDATION_PASSWORD_TEXT_LENGTH
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
    private fun validatePasswordMatch(){
        binding.apply {
            editTextConfirmPassword.addTextChangedListener {
                if(editTextPassword.text.toString() == editTextConfirmPassword.text.toString()){
                    textViewValidateConfirm.visibility = View.GONE
                    validationConfirm = true
                }
                else{
                    textViewValidateConfirm.text = ERROR_VALIDATION_CONFIRM_PASSWORD_MISMATCH
                    textViewValidateConfirm.visibility = View.VISIBLE
                    validationConfirm = false
                }
            }
        }
    }
    private fun registerButtonClickHandler(){
        binding.buttonRegister.setOnClickListener {
            if(validationUserName && validationPassword && validationConfirm){
                val auth = AuthService(onFailure = {

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

     companion object{
        const val VALIDATION_PASSWORD_LENGTH = 8
        const val VALIDATION_USERNAME_LENGTH = 3
        const val ERROR_VALIDATION_PASSWORD_TEXT_LENGTH = "Please should be greater than 8 character"
        const val ERROR_VALIDATION_USER_NAME_SPECIAL = "Should don't have special %$@.."
        const val ERROR_VALIDATION_USER_NAME_SPACE = "Should don't have space"
        const val ERROR_VALIDATION_USER_NAME_START_WITH_DIGIT = "Should not start with number"
        const val ERROR_VALIDATION_USER_NAME_SHOULD_GRATER_THE_LIMIT = "Should length grater than $VALIDATION_USERNAME_LENGTH"
        const val ERROR_VALIDATION_CONFIRM_PASSWORD_MISMATCH = "The password mismatch"
    }
}


