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


class LoginFragment : BaseFragment<FragmentLoginBinding>() , LoginView{

    private var presenter: LoginPresenter = LoginPresenter(this)
    val userRequest : UserRequest = UserRequest(username = "Jinan" , password = "123457777")

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.buttonLogin.setOnClickListener {
            presenter.clickableLoginButton(userRequest)
        }
    }

    override fun onFailureLogin(message: String?) {
        Log.i("login" ,"message onFailureLogin" + message.toString())
    }


    override fun onSuccessLogin() {

    }

    override fun onSuccessResponse(message: String?) {
        Log.i("login" ,"message onSuccessResponse" + message.toString())
    }


}