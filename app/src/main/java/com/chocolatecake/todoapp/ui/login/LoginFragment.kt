package com.chocolatecake.todoapp.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.databinding.FragmentLoginBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.login.presenter.LoginPresenter

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {
    private var presenter: LoginPresenter = LoginPresenter(this)
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.buttonLogin.setOnClickListener {
            val userRequest = UserRequest(
                username = binding.editTextUsername.text.toString(),
                password = binding.editTextInputPassword.text.toString(),
            )
            presenter.clickableLoginButton(userRequest)
        }
    }

    override fun onFailureLogin(message: String?) {
        println("hghhgghghghhghghghghghghg"+ message)
        Log.i("login", "message onFailureLogin $message")
    }

    override fun onSuccessLogin() {
        //to nav home page
    }

    override fun onFailureResponse(message: String?) {
        Log.i("login", "message onFailureResponse $message")
    }
}