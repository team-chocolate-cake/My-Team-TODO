package com.chocolatecake.todoapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.chocolatecake.todoapp.databinding.FragmentLoginBinding
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.util.*
import com.chocolatecake.todoapp.features.base.fragment.BaseFragment
import com.chocolatecake.todoapp.features.home.view.HomeFragment
import com.chocolatecake.todoapp.features.register.RegisterFragment
import com.chocolatecake.todoapp.features.login.presenter.LoginPresenter


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    private val presenter by lazy {
        LoginPresenter(
            view = this,
            TaskSharedPreferences(requireActivity().applicationContext)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
        addCallBacks()
    }

    private fun setUp() {
        checkUsernameValidate()
        checkPasswordValidate()
    }

    private fun addCallBacks() {
        onClickLoginButton()
        onClickRegisterButton()
    }

    private fun onClickLoginButton() {
        binding.buttonLogin.setOnClickListener {
            presenter.login(
                UserRequest(
                    username = binding.editTextUsername.text.toString().trim(),
                    password = binding.editTextInputPassword.text.toString().trim(),
                ),
            )
        }
    }


    private fun checkUsernameValidate() {
        binding.editTextUsername.apply {
            doOnTextChanged { text, start, before, count ->
                presenter.checkUsernameValidate(text.toString())
            }
        }
    }

    private fun checkPasswordValidate() {
        binding.editTextInputPassword.apply {
            doOnTextChanged { text, start, before, count ->
                presenter.checkPasswordValidate(text.toString())
            }
        }
    }


    override fun navigateToHomeScreen() {
        requireActivity().navigateExclusive(HomeFragment())
    }

    private fun onClickRegisterButton() {
        binding.textViewRegisterBody.setOnClickListener {
            requireActivity().navigateExclusive(RegisterFragment())
        }
    }


    override fun showLoginFailedError(message: String?) {
        requireActivity().runOnUiThread {
            requireActivity().showSnackbar(message = message, binding.root)
        }
    }

    override fun showUsernameValidationFailedError(visible: Boolean) {
        if (visible) {
            binding.textViewUsernameValidate.show()
        } else {
            binding.textViewUsernameValidate.hide()
        }
    }

    override fun showPasswordValidationFailedError(visible: Boolean) {
        if (visible) {
            binding.textViewPasswordValidate.show()
        } else {
            binding.textViewPasswordValidate.hide()
        }
    }

    override fun showProgressBar(visible: Boolean) {
        requireActivity().runOnUiThread {
            if (visible) {
                binding.progressBar.show()
                binding.buttonLogin.hide()
            } else {
                binding.progressBar.hide()
                binding.buttonLogin.show()
            }
        }
    }
}