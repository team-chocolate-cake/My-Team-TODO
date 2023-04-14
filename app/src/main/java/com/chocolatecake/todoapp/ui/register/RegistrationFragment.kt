package com.chocolatecake.todoapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.LoginResponse
import com.chocolatecake.todoapp.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.presenter.RegistrationPresenter
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.HomeFragment
import com.chocolatecake.todoapp.ui.login.LoginFragment
import com.chocolatecake.todoapp.util.getUsernameStatus

class RegistrationFragment : BaseFragment<FragmentRegisterBinding>(), RegisterView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    private var validationUserName: Boolean = false
    private var validationPassword: Boolean = false
    private var validationConfirmPassword: Boolean = false

    private val registrationPresenter: RegistrationPresenter by lazy {
        RegistrationPresenter(
            this,
            requireContext()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
        setupUsernameValidation()
        setPasswordValidation()
        validatePasswordMatch()
        registerButtonClickHandler()
    }

    private fun addCallBacks() {
        binding.apply {
            textViewLogin.setOnClickListener {
                changeFragment(LoginFragment())
            }
        }
    }

    private fun setupUsernameValidation() {
        binding.apply {
            textInputEditTextLayoutUsername.addTextChangedListener {
                when (getUsernameStatus(
                    textInputEditTextLayoutUsername.text.toString(),
                    requireContext()
                )) {
                    getString(R.string.error_validation_user_name_special) ->
                        setErrorUsername(getString(R.string.error_validation_user_name_special))
                    getString(R.string.error_validation_user_name_space) ->
                        setErrorUsername(getString(R.string.error_validation_user_name_space))
                    getString(R.string.error_validation_user_name_should_grater_the_limit) ->
                        setErrorUsername(getString(R.string.error_validation_user_name_should_grater_the_limit))
                    getString(R.string.error_validation_user_name_start_with_digit) -> {
                        setErrorUsername(getString(R.string.error_validation_user_name_start_with_digit))
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
            textInputEditTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength < VALIDATION_PASSWORD_LENGTH -> {
                        textViewValidatePassword.text =
                            getText(R.string.error_validation_password_text_length)
                        textViewValidatePassword.visibility = View.VISIBLE
                        validationPassword = false
                    }
                    else -> {
                        textViewValidatePassword.visibility = View.GONE
                        validationPassword = true
                    }
                }
            }
        }
    }

    private fun validatePasswordMatch() {
        binding.apply {
            textInputEditTextConfirmPassword.addTextChangedListener {
                if (textInputEditTextPassword.text.toString() == textInputEditTextConfirmPassword.text.toString()) {
                    textViewValidateConfirm.visibility = View.GONE
                    validationConfirmPassword = true
                } else {
                    textViewValidateConfirm.text =
                        getText(R.string.error_validation_confirm_password_mismatch)
                    textViewValidateConfirm.visibility = View.VISIBLE
                    validationConfirmPassword = false
                }
            }
        }
    }

    private fun setErrorUsername(error: String) {
        activity?.runOnUiThread {
            binding.textViewValidateUserName.text = error
            binding.textViewValidateUserName.visibility = View.VISIBLE
            validationUserName = false
        }
    }

    private fun registerButtonClickHandler() {
        binding.buttonRegister.setOnClickListener { buttonRegister ->
            if (validationUserName && validationPassword && validationConfirmPassword) {
                val newUser = UserRequest(binding.textInputEditTextLayoutUsername.text.toString(), binding.textInputEditTextPassword.text.toString())
                registrationPresenter.makeRequest(newUser)
                buttonRegister.visibility = View.GONE
                binding.progressBarReload.visibility = View.VISIBLE
            }
            checkFields()
        }
    }

    private fun checkFields() {
        if (binding.textInputEditTextLayoutUsername.text.isNullOrEmpty()) {
            binding.apply {
                textViewValidateUserName.visibility = View.VISIBLE
                textViewValidateUserName.text = getString(R.string.username_cannot_be_empty)
            }
        }
        if (binding.textInputEditTextPassword.text.isNullOrEmpty()) {
            binding.apply {
                textViewValidatePassword.visibility = View.VISIBLE
                textViewValidatePassword.text = getText(R.string.password_cannot_be_empty)
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onRegisterSuccess(response: RegisterResponse) {
        requireActivity().runOnUiThread {
            binding.progressBarReload.visibility = View.GONE
            binding.buttonRegister.visibility = View.VISIBLE
            changeFragment(HomeFragment())
        }
    }

    override fun onRegisterFailure(message: String?) {
        message?.let { setErrorUsername(it) }
    }

    override fun onLoginSuccess(response: LoginResponse) {
        requireActivity().runOnUiThread {
            binding.progressBarReload.visibility = View.GONE
            binding.buttonRegister.visibility = View.VISIBLE
            if(response.value?.token!!.isNotEmpty()){
                changeFragment(HomeFragment())
            }else{
                changeFragment(LoginFragment())
            }
        }
    }

    override fun onFailure(message: String?) {
        requireActivity().runOnUiThread {
            binding.progressBarReload.visibility = View.GONE
            setErrorUsername(getString(R.string.no_internet_connection))
        }
    }

    companion object {
        const val VALIDATION_PASSWORD_LENGTH = 8
        const val VALIDATION_USERNAME_LENGTH = 4
    }
}