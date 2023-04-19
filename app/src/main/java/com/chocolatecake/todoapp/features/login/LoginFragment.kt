package com.chocolatecake.todoapp.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.databinding.FragmentLoginBinding
import com.chocolatecake.todoapp.features.base.fragment.BaseFragment
import com.chocolatecake.todoapp.core.data.local.TaskSharedPreferences
import com.chocolatecake.todoapp.core.data.model.request.UserRequest
import com.chocolatecake.todoapp.core.util.*
import com.chocolatecake.todoapp.features.home.view.HomeFragment
import com.chocolatecake.todoapp.features.login.presenter.LoginPresenter
import com.chocolatecake.todoapp.features.login.util.passwordLength
import com.chocolatecake.todoapp.features.login.util.usernameLength
import com.chocolatecake.todoapp.features.register.RegisterFragment


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
        onCallBacks()
    }

    private fun onCallBacks() {
        onClickLoginButton()
        checkUsernameValidate()
        checkPasswordValidate()
        onClickRegisterButton()
    }

    private fun onClickLoginButton() {
        binding.buttonLogin.setOnClickListener {
            val userRequest = UserRequest(
                username = binding.editTextUsername.text.toString().trim(),
                password = binding.editTextInputPassword.text.toString().trim(),
            )

            userRequest.apply {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    if (!username.usernameLength() && !password.passwordLength()) {
                        presenter.login(this)
                        showProgressBar()
                    }
                } else {
                    requireActivity().showSnackbar(
                        message = getString(R.string.please_fill_fields),
                        binding.root
                    )
                }
            }
        }
    }


    private fun checkUsernameValidate() {
        binding.editTextUsername.apply {
            doOnTextChanged { text, start, before, count ->
                if (text.toString().usernameLength() && text.toString().isNotEmpty()) {
                    binding.textViewUsernameValidate.show()
                } else {
                    binding.textViewUsernameValidate.hide()
                }
            }
        }
    }

    private fun checkPasswordValidate() {
        binding.editTextInputPassword.apply {
            doOnTextChanged { text, start, before, count ->
                if (text.toString().passwordLength() && text.toString().isNotEmpty()) {
                    binding.textViewPasswordValidate.show()
                } else {
                    binding.textViewPasswordValidate.hide()
                }
            }
        }
    }

    override fun onFailure(message: String?) {
        requireActivity().runOnUiThread {
            requireActivity().showSnackbar(message = message, binding.root)
            hideProgressBar()
        }
    }

    override fun onSuccessLogin() {
        requireActivity().navigateExclusive(HomeFragment())
    }

    override fun showLoginError() {

    }

    private fun onClickRegisterButton() {
        binding.textViewRegisterBody.setOnClickListener {
            requireActivity().navigateExclusive(RegisterFragment())
        }
    }

    private fun showProgressBar() {
        binding.buttonLogin.hide()
        binding.progressBar.show()
    }

    private fun hideProgressBar() {
        binding.progressBar.hide()
        binding.buttonLogin.show()
    }
}