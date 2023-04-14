package com.chocolatecake.todoapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.databinding.FragmentLoginBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.HomeFragment
import com.chocolatecake.todoapp.ui.login.presenter.LoginPresenter
import com.chocolatecake.todoapp.ui.register.RegisterFragment
import com.chocolatecake.todoapp.util.*
import com.google.android.material.snackbar.Snackbar


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {
    private val presenter by lazy { LoginPresenter(view = this, context = requireContext()) }
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callsBack()
    }

    private fun callsBack() {
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
            if (userRequest.username.isNotEmpty() && userRequest.password.isNotEmpty()) {
                presenter.clickableLoginButton(userRequest)
                showProgressBar()
            } else {
                requireActivity().showSnackbar(message = "fill fields please" ,binding.root)
            }
        }
    }

    fun showProgressBar(){
        binding.buttonLogin.hide()
        binding.progressBar.show()
    }

    fun hideProgressBar(){
        binding.progressBar.hide()
        binding.buttonLogin.show()
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
            requireActivity().showSnackbar(message = message ,binding.root)
            hideProgressBar()
        }
    }

    override fun onSuccessLogin() {
        requireActivity().navigateExclusive(HomeFragment())
    }

    private fun onClickRegisterButton() {
        binding.textViewRegisterBody.setOnClickListener {
            requireActivity().navigateTo(RegisterFragment())
        }
    }
}