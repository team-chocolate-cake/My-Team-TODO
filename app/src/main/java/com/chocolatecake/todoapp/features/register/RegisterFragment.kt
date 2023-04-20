package com.chocolatecake.todoapp.features.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.core.util.*
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.features.register.presenter.RegisterPresenter
import com.chocolatecake.todoapp.features.base.fragment.BaseFragment
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.util.hide
import com.chocolatecake.todoapp.core.util.show
import com.chocolatecake.todoapp.core.util.showSnackbar
import com.chocolatecake.todoapp.features.home.view.HomeFragment
import com.chocolatecake.todoapp.features.login.LoginFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), RegisterView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    private var validationUserName: Boolean = false
    private var validationPassword: Boolean = false
    private var validationConfirmPassword: Boolean = false

    private val registerPresenter: RegisterPresenter by lazy {
        RegisterPresenter(this, TaskSharedPreferences(requireActivity().applicationContext))
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
        binding.textInputEditTextLayoutUsername.addTextChangedListener {
                registerPresenter.checkUsernameText(
                    binding.textInputEditTextLayoutUsername.text.toString(),
                    requireContext()
                )
            }
        }

    private fun checkPasswordValidate() {
        binding.textInputEditTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                registerPresenter.checkPasswordText(passwordLength)
            }
        }

    private fun checkConfirmPasswordValidate() {
        binding.textInputEditTextConfirmPassword.addTextChangedListener {
                registerPresenter.checkConfirmPasswordText(
                    binding.textInputEditTextPassword.text.toString(),
                    binding.textInputEditTextConfirmPassword.text.toString()
                )
            }
        }

    private fun onClickRegisterButton() {
        binding.buttonRegister.setOnClickListener {
            val isValidateFailed = validationUserName && validationPassword && validationConfirmPassword
            val usernameText = binding.textInputEditTextLayoutUsername.text.toString()
            val passwordText = binding.textInputEditTextPassword.text.toString()
            val confirmPasswordText = binding.textInputEditTextConfirmPassword.text.toString()
           registerPresenter.handleRegister(isValidateFailed,usernameText,passwordText, confirmPasswordText)
        }
    }

    override fun navigationToHome() {
        activity?.runOnUiThread {
                binding.progressBarReload.hide()
                binding.buttonRegister.show()
                activity?.navigateExclusive((HomeFragment()))
            }
    }

    override fun showErrorRegister(message: String?) {
        activity?.runOnUiThread {
            binding.progressBarReload.hide()
            binding.buttonRegister.show()
            message?.let { registerPresenter.setErrorUsername(it) }
        }
    }

    override fun showNoInternetConnection(message: String?) {
        activity?.runOnUiThread {
            activity?.showSnackbar(getString(R.string.no_internet_connection), binding.root)
            binding.progressBarReload.hide()
            binding.buttonRegister.show()
        }
    }

    override fun hideUsername() {
        binding.textViewValidateUserName.hide()
        validationUserName = true
    }

    override fun showErrorInvalidUsername(message: String?) {
        activity?.runOnUiThread {
            binding.textViewValidateUserName.text = message
            binding.textViewValidateUserName.show()
            validationUserName = false
        }
    }

    override fun showErrorPasswordLength() {
        binding.textViewValidatePassword.text = getText(R.string.error_validation_password_text_length)
        binding.textViewValidatePassword.show()
        validationPassword = false
    }

    override fun hideValidatePasswordText() {
        binding.textViewValidatePassword.hide()
        validationPassword = true
    }

    override fun showConfirmPassword(isVisible: Boolean) {
        if (isVisible) {
            binding.textViewValidateConfirm.hide()
            validationConfirmPassword = true
        } else {
            binding.textViewValidateConfirm.text = getText(R.string.error_validation_confirm_password_mismatch)
            binding.textViewValidateConfirm.show()
            validationConfirmPassword = false
        }
    }

    override fun registerUser() {
        val newUser = UserRequest(
            binding.textInputEditTextLayoutUsername.text.toString(),
            binding.textInputEditTextPassword.text.toString()
        )
        registerPresenter.makeRequest(newUser)
        binding.buttonRegister.hide()
        binding.progressBarReload.show()
    }

    override fun showEmptyUsernameError() {
        binding.apply {
            textViewValidateUserName.show()
            textViewValidateUserName.text = getString(R.string.username_cannot_be_empty)
        }
    }

    override fun showEmptyPasswordError() {
        binding.apply {
            textViewValidatePassword.show()
            textViewValidatePassword.text = getText(R.string.password_cannot_be_empty)
        }
    }

    override fun showMismatchConfirmPassword() {
        binding.textViewValidateConfirm.show()
        binding.textViewValidateConfirm.text = getText(R.string.error_validation_confirm_password_mismatch)
    }

}