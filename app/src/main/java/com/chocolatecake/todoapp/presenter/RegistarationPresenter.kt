package com.chocolatecake.todoapp.presenter

import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.ui.register.IRegisterView
import com.google.gson.Gson

class RegistrationPresenter {
    lateinit var iRegisterView: IRegisterView
    fun makeRequest(userRequest: UserRequest) {
        val auth = AuthService()
        auth.register(userRequest,
            onSuccess = {
                val body = it?.body?.string().toString()
                val registerResponse = Gson().fromJson(body, RegisterResponse::class.java)
                iRegisterView.onSuccess(registerResponse)
            },
            onFailure = { iRegisterView.onFailure(it) })
    }
}
