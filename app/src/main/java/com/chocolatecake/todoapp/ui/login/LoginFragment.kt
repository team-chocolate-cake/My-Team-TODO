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
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.databinding.FragmentLoginBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.HomeFragment
import com.chocolatecake.todoapp.ui.login.presenter.LoginPresenter
import com.chocolatecake.todoapp.util.*
import com.google.android.material.snackbar.Snackbar


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {
    private var username: String = ""
    private var password: String = ""
    private val presenter by lazy { LoginPresenter(view = this, context = requireContext()) }
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callBacks()
    }

    private fun callBacks() {
        onClickLoginButton()
        checkUsernameValidate()
        checkPasswordValidate()
    }

    private fun onClickLoginButton() {
        binding.buttonLogin.setOnClickListener {
            val userRequest = UserRequest(
                username = binding.editTextUsername.text.toString().trim(),
                password = binding.editTextInputPassword.text.toString().trim(),
            )
            if (userRequest.username.isNotEmpty() && userRequest.password.isNotEmpty()) {
                presenter.clickableLoginButton(userRequest)
            } else {
                showSnackbar("fill fields please")
            }
        }
    }

    private fun checkUsernameValidate() {
        if (username.usernameLength() == true) {
            binding.editTextUsername.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    username = s.toString()
                    binding.textViewUsernameValidate.show()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        } else if (username.usernameLength() == false)  {
            binding.textViewUsernameValidate.hide()
        }
    }

    private fun checkPasswordValidate() {
        if (password.passwordLength())
            binding.editTextInputPassword.setOnClickListener {
                password = it.toString()
                binding.textViewPasswordValidate.show()
            } else {
            binding.textViewPasswordValidate.hide()
        }
    }

    override fun onFailure(message: String?) {
        Log.i("login", "message onFailureLogin $message")

        requireActivity().runOnUiThread {
            showSnackbar(message = message)
        }
    }

    override fun onSuccessLogin() {
        requireActivity().navigateExclusive(HomeFragment())
    }

    private fun showSnackbar(message: String?) {
        Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_LONG)
            .setAction("Ok") {}
            .show()
    }
}