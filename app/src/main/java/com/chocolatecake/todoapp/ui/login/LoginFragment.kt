package com.chocolatecake.todoapp.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.databinding.ActivityHomeBinding
import com.chocolatecake.todoapp.databinding.FragmentLoginBinding
import com.chocolatecake.todoapp.ui.activity.HomeActivity
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.login.presenter.LoginPresenter

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {
    private val presenter by lazy { LoginPresenter(view= this, context= HomeActivity()) }
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

        requireActivity().runOnUiThread {
            showToast(message= message)
        }

    }

    override fun onSuccessLogin() {
        //to nav home page
    }

    override fun onFailureResponse(message: String?) {
        requireActivity().runOnUiThread {
            showToast(message= message)
        }
        Log.i("login", "message onFailureResponse $message")
    }

    private fun showToast(message:String?){
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}