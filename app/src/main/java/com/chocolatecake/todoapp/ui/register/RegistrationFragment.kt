package com.chocolatecake.todoapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.presenter.RegistrationPresenter
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.HomeFragment
import com.chocolatecake.todoapp.ui.login.LoginFragment
import com.chocolatecake.todoapp.util.getUsernameStatus

class RegistrationFragment : BaseFragment<FragmentRegisterBinding>(), IRegisterView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    private var validationUserName: Boolean = false
    private var validationPassword: Boolean = false
    private var validationConfirm: Boolean = false
    private var registrationPresenter = RegistrationPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
        setupUsernameValidation()
        setPasswordValidation()
        validatePasswordMatch()
        registerButtonClickHandler()
    }

    private fun addCallBacks() {
        registrationPresenter.iRegisterView = this
        binding.apply {
            textViewLogin.setOnClickListener {
                changeFragment(LoginFragment())
            }
        }
    }

    private fun setupUsernameValidation() {
        binding.apply {
            textInputTextInputLayoutUsername.addTextChangedListener {
                when (getUsernameStatus(
                    textInputTextInputLayoutUsername.text.toString(),
                    requireContext()
                )) {
                    getString(R.string.error_validation_user_name_special) -> {
                        setErrorUsername(getString(R.string.error_validation_user_name_special))
                    }
                    getString(R.string.error_validation_user_name_space) -> {
                        setErrorUsername(getString(R.string.error_validation_user_name_space))
                    }
                    getString(R.string.error_validation_user_name_should_grater_the_limit) -> {
                        setErrorUsername(getString(R.string.error_validation_user_name_should_grater_the_limit))
                    }
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
                    passwordText.toString().isEmpty() -> {
                        textViewValidatePassword.text =
                            getText(R.string.password_cannot_be_empty)
                        textViewValidatePassword.visibility = View.VISIBLE
                        validationPassword = false
                    }
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
                    validationConfirm = true
                } else if (textInputEditTextPassword.text.isNullOrEmpty()) {
                    textViewValidateConfirm.text =
                        getText(R.string.confirm_password_cannot_be_empty)
                    textViewValidateConfirm.visibility = View.VISIBLE
                    validationConfirm = false

                } else {
                    textViewValidateConfirm.text =
                        getText(R.string.error_validation_confirm_password_mismatch)
                    textViewValidateConfirm.visibility = View.VISIBLE
                    validationConfirm = false
                }
            }
        }
    }

    private fun setErrorUsername(error: String) {
        binding.textViewValidateUserName.text = error
        binding.textViewValidateUserName.visibility = View.VISIBLE
        validationUserName = false
    }

    private fun registerButtonClickHandler() {
        binding.buttonRegister.setOnClickListener {
            if (validationUserName && validationPassword && validationConfirm) {
                val username = binding.textInputTextInputLayoutUsername.text.toString()
                val password = binding.textInputEditTextPassword.text.toString()
                val newUser = UserRequest(username, password)
                registrationPresenter.makeRequest(newUser)
                it.visibility = View.GONE
                binding.progressBarReload.visibility = View.VISIBLE
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

    override fun onSuccess(response: RegisterResponse) {
        requireActivity().runOnUiThread {
            binding.progressBarReload.visibility = View.GONE
            binding.buttonRegister.visibility = View.VISIBLE
            if (!response.isSuccess) {
                setErrorUsername(getString(R.string.error_validation_user_name_already_exists))
            } else {
                changeFragment(HomeFragment())
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
        const val VALIDATION_USERNAME_LENGTH = 3
    }
}