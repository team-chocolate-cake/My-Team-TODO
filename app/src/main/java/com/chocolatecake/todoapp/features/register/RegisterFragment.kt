package com.chocolatecake.todoapp.features.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.core.util.*
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.features.register.presenter.RegistrationPresenter
import com.chocolatecake.todoapp.features.base.fragment.BaseFragment
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.core.util.hide
import com.chocolatecake.todoapp.core.util.show
import com.chocolatecake.todoapp.core.util.showSnackbar
import com.chocolatecake.todoapp.features.home.view.HomeFragment
import com.chocolatecake.todoapp.features.register.util.getUsernameStatus
import com.chocolatecake.todoapp.features.login.LoginFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), RegisterView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    private var validationUserName: Boolean = false
    private var validationPassword: Boolean = false
    private var validationConfirmPassword: Boolean = false

    private val registrationPresenter: RegistrationPresenter by lazy {
        RegistrationPresenter(this, TaskSharedPreferences(requireActivity().applicationContext))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
    }

    private fun addCallBacks() {
        checkUsernameValidate()
        checkPasswordValidate()
        checkConfirmPasswordValidate()
        onClickRegisterButton()
        navigationToLoginScreen()
    }

    private fun navigationToLoginScreen() {
        binding.textViewLogin.setOnClickListener {
            activity?.navigateExclusive(LoginFragment())
        }
    }

    private fun checkUsernameValidate() {
        binding.apply {
            textInputEditTextLayoutUsername.addTextChangedListener {
                when (getUsernameStatus(
                    textInputEditTextLayoutUsername.text.toString(),
                    requireContext()
                )) {
                    getString(R.string.error_validation_user_name_special) -> setErrorUsername(
                        getString(R.string.error_validation_user_name_special)
                    )
                    getString(R.string.error_validation_user_name_space) -> setErrorUsername(
                        getString(R.string.error_validation_user_name_space)
                    )
                    getString(R.string.error_validation_user_name_should_grater_the_limit) -> setErrorUsername(
                        getString(R.string.error_validation_user_name_should_grater_the_limit)
                    )
                    getString(R.string.error_validation_user_name_start_with_digit) -> setErrorUsername(
                        getString(R.string.error_validation_user_name_start_with_digit)
                    )
                    else -> {
                        textViewValidateUserName.hide()
                        validationUserName = true
                    }
                }
            }
        }
    }

    private fun checkPasswordValidate() {
        binding.apply {
            textInputEditTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength < VALIDATION_PASSWORD_LENGTH -> {
                        textViewValidatePassword.text =
                            getText(R.string.error_validation_password_text_length)
                        textViewValidatePassword.show()
                        validationPassword = false
                    }
                    else -> {
                        textViewValidatePassword.hide()
                        validationPassword = true
                    }
                }
            }
        }
    }

    private fun checkConfirmPasswordValidate() {
        binding.apply {
            textInputEditTextConfirmPassword.addTextChangedListener {
                if (textInputEditTextPassword.text.toString() == textInputEditTextConfirmPassword.text.toString()) {
                    textViewValidateConfirm.hide()
                    validationConfirmPassword = true
                } else {
                    textViewValidateConfirm.text =
                        getText(R.string.error_validation_confirm_password_mismatch)
                    textViewValidateConfirm.show()
                    validationConfirmPassword = false
                }
            }
        }
    }

    private fun setErrorUsername(error: String) {
        activity?.runOnUiThread {
            binding.textViewValidateUserName.text = error
            binding.textViewValidateUserName.show()
            validationUserName = false
        }
    }

    private fun onClickRegisterButton() {
        binding.buttonRegister.setOnClickListener { buttonRegister ->
            if (validationUserName && validationPassword && validationConfirmPassword) {
                val newUser = UserRequest(
                    binding.textInputEditTextLayoutUsername.text.toString(),
                    binding.textInputEditTextPassword.text.toString()
                )
                registrationPresenter.makeRequest(newUser)
                buttonRegister.hide()
                binding.progressBarReload.show()
            }
            if (binding.textInputEditTextLayoutUsername.text.isNullOrEmpty()) {
                binding.apply {
                    textViewValidateUserName.show()
                    textViewValidateUserName.text = getString(R.string.username_cannot_be_empty)
                }
            }
            if (binding.textInputEditTextPassword.text.isNullOrEmpty()) {
                binding.apply {
                    textViewValidatePassword.show()
                    textViewValidatePassword.text = getText(R.string.password_cannot_be_empty)
                }
            }
            if (binding.textInputEditTextConfirmPassword.text.toString() != binding.textInputEditTextPassword.text.toString()) {
                binding.textViewValidateConfirm.show()
                binding.textViewValidateConfirm.text =
                    getText(R.string.error_validation_confirm_password_mismatch)
            }
        }
    }

    override fun onRegisterSuccess(response: RegisterResponse) {
        activity?.apply {
            runOnUiThread {
                binding.progressBarReload.hide()
                binding.buttonRegister.show()
            }
        }
    }

    override fun onRegisterFailure(message: String?) {
        activity?.runOnUiThread {
            binding.progressBarReload.hide()
            binding.buttonRegister.show()
            message?.let { setErrorUsername(it) }
        }
    }

    override fun onLoginSuccess() {
        activity?.apply {
            runOnUiThread {
                binding.progressBarReload.hide()
                binding.buttonRegister.show()
                navigateExclusive((HomeFragment()))
            }
        }
    }

    override fun onFailure(message: String?) {
        activity?.runOnUiThread {
            activity?.showSnackbar(
                message = getString(R.string.no_internet_connection),
                binding.root
            )
            binding.progressBarReload.hide()
            binding.buttonRegister.show()
        }
    }

    companion object {
        const val VALIDATION_PASSWORD_LENGTH = 8
        const val VALIDATION_USERNAME_LENGTH = 4
    }
}