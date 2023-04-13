package com.chocolatecake.todoapp.ui.login.presenter

import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.LoginResponse
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.ui.login.LoginView
import com.google.gson.Gson

class LoginPresenter(
    private val view :LoginView
) {
    private val authService: AuthService by lazy{
        AuthService()
    }

    fun clickableLoginButton(userRequest: UserRequest){
        authService.login(
            userRequest = userRequest,
            onFailure = {
                view.onFailureLogin(it)
            },
            onSuccess = {
                val auth = it.body?.string().toString()
                val loginResponse = Gson().fromJson(auth, LoginResponse::class.java)

                onSuccessResponse(loginResponse)

            },
        )
    }

    private fun onSuccessResponse(loginResponse : LoginResponse) {

        when (loginResponse.isSuccess) {
            true -> {

                view.onSuccessLogin()
                /// token
                ////expire time
            }
            else -> {
                view.onSuccessResponse(loginResponse.message)
            }
        }

    }

}